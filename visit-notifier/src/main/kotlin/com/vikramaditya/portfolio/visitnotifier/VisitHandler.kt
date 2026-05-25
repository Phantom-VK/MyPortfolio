package com.vikramaditya.portfolio.visitnotifier

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets
import java.time.Clock
import java.time.Instant
import java.util.logging.Level
import java.util.logging.Logger

class VisitHandler(
    private val config: AppConfig,
    private val emailSender: EmailSender,
    private val deduper: VisitDeduper,
    private val rateLimiter: RateLimiter,
    private val clock: Clock = Clock.systemUTC(),
    private val json: Json = Json { ignoreUnknownKeys = false },
    private val logger: Logger = Logger.getLogger(VisitHandler::class.java.name),
) {
    fun handleVisit(exchange: HttpExchange) {
        when (exchange.requestMethod.uppercase()) {
            "OPTIONS" -> handlePreflight(exchange)
            "POST" -> handlePost(exchange)
            else -> respond(exchange, HttpURLConnection.HTTP_BAD_METHOD, "method_not_allowed")
        }
    }

    fun handleHealth(exchange: HttpExchange) {
        if (exchange.requestMethod.uppercase() != "GET") {
            respond(exchange, HttpURLConnection.HTTP_BAD_METHOD, "method_not_allowed")
            return
        }
        respond(exchange, HttpURLConnection.HTTP_OK, "ok", contentType = "text/plain; charset=utf-8")
    }

    private fun handlePreflight(exchange: HttpExchange) {
        val origin = exchange.requestHeaders.getFirst("Origin")
        if (!isOriginAllowed(origin)) {
            respond(exchange, HttpURLConnection.HTTP_FORBIDDEN, "origin_not_allowed")
            return
        }
        withCors(exchange.responseHeaders, origin)
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, -1)
        exchange.close()
    }

    private fun handlePost(exchange: HttpExchange) {
        val origin = exchange.requestHeaders.getFirst("Origin")
        if (!isOriginAllowed(origin)) {
            respond(exchange, HttpURLConnection.HTTP_FORBIDDEN, "origin_not_allowed")
            return
        }

        val metadata = extractRequestMetadata(exchange)
        if (!rateLimiter.allow(metadata.ip)) {
            withCors(exchange.responseHeaders, origin)
            respond(exchange, 429, "rate_limited")
            return
        }

        val rawBody = try {
            readRequestBody(exchange, config.maxPayloadBytes)
        } catch (_: PayloadTooLargeException) {
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "payload_too_large")
            return
        }

        val payload = try {
            json.decodeFromString<VisitPayload>(rawBody)
        } catch (_: SerializationException) {
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "invalid_json")
            return
        }

        try {
            VisitValidator.validate(payload)
        } catch (validationError: VisitValidationException) {
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_BAD_REQUEST, validationError.message ?: "invalid_payload")
            return
        }

        if (!deduper.shouldProcess(payload.sessionId, payload.path)) {
            logger.info("visit_suppressed ip=${metadata.ip} path=${payload.path}")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_ACCEPTED, "duplicate_suppressed")
            return
        }

        val visit = AcceptedVisit(
            payload = payload,
            requestMetadata = metadata,
            receivedAtIso = Instant.now(clock).toString(),
        )

        try {
            emailSender.sendVisitNotification(visit)
            logger.info("visit_accepted ip=${metadata.ip} path=${payload.path}")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_ACCEPTED, "accepted")
        } catch (sendError: Exception) {
            deduper.release(payload.sessionId, payload.path)
            logger.log(Level.SEVERE, "visit_send_failed ip=${metadata.ip} path=${payload.path}", sendError)
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "send_failed")
        }
    }

    private fun extractRequestMetadata(exchange: HttpExchange): RequestMetadata {
        val headers = exchange.requestHeaders
        val forwardedFor = headers.getFirst("X-Forwarded-For")
        val realIp = headers.getFirst("CF-Connecting-IP")
            ?: headers.getFirst("True-Client-IP")
            ?: headers.getFirst("X-Real-IP")
        val ip = forwardedFor
            ?.split(",")
            ?.firstOrNull()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: realIp?.takeIf { it.isNotBlank() }
            ?: exchange.remoteAddress.address?.hostAddress
            ?: exchange.remoteAddress.hostString
        return RequestMetadata(
            ip = ip,
            userAgentHeader = headers.getFirst("User-Agent"),
            origin = headers.getFirst("Origin"),
            host = headers.getFirst("Host"),
            forwardedFor = forwardedFor,
            realIp = realIp,
        )
    }

    private fun readRequestBody(exchange: HttpExchange, maxBytes: Int): String {
        val input = exchange.requestBody
        val buffer = ByteArray(1024)
        val output = ByteArrayOutputStream()
        var total = 0
        while (true) {
            val read = input.read(buffer)
            if (read < 0) break
            total += read
            if (total > maxBytes) {
                throw PayloadTooLargeException()
            }
            output.write(buffer, 0, read)
        }
        return output.toString(StandardCharsets.UTF_8)
    }

    private fun isOriginAllowed(origin: String?): Boolean {
        return origin != null && config.allowedOrigins.contains(origin)
    }

    private fun withCors(headers: Headers, origin: String?) {
        if (origin != null && config.allowedOrigins.contains(origin)) {
            headers["Access-Control-Allow-Origin"] = listOf(origin)
            headers["Vary"] = listOf("Origin")
            headers["Access-Control-Allow-Methods"] = listOf("POST, OPTIONS")
            headers["Access-Control-Allow-Headers"] = listOf("Content-Type")
        }
    }

    private fun respond(
        exchange: HttpExchange,
        status: Int,
        body: String,
        contentType: String = "text/plain; charset=utf-8",
    ) {
        exchange.responseHeaders["Content-Type"] = listOf(contentType)
        val bytes = body.toByteArray(StandardCharsets.UTF_8)
        exchange.sendResponseHeaders(status, bytes.size.toLong())
        exchange.responseBody.use { output -> output.write(bytes) }
        exchange.close()
    }

    private class PayloadTooLargeException : RuntimeException()
}
