package com.vikramaditya.portfolio.visitnotifier

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.io.IOException
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
    private val json: Json = Json { ignoreUnknownKeys = true },
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
            logger.warning("preflight_rejected origin=$origin")
            respond(exchange, HttpURLConnection.HTTP_FORBIDDEN, "origin_not_allowed")
            return
        }
        withCors(exchange.responseHeaders, origin)
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, -1)
        exchange.close()
    }

    private fun handlePost(exchange: HttpExchange) {
        // Log immediately — before any check — so even cold-start buffering doesn't lose this line
        val origin = exchange.requestHeaders.getFirst("Origin")
        val metadata = extractRequestMetadata(exchange)
        logger.info("visit_received ip=${metadata.ip} origin=$origin")

        if (!isOriginAllowed(origin)) {
            logger.warning("post_rejected_origin ip=${metadata.ip} origin=$origin")
            respond(exchange, HttpURLConnection.HTTP_FORBIDDEN, "origin_not_allowed")
            return
        }

        if (!rateLimiter.allow(metadata.ip)) {
            logger.warning("visit_rate_limited ip=${metadata.ip}")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, 429, "rate_limited")
            return
        }

        val rawBody = try {
            readRequestBody(exchange, config.maxPayloadBytes)
        } catch (_: PayloadTooLargeException) {
            logger.warning("visit_payload_too_large ip=${metadata.ip}")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "payload_too_large")
            return
        }

        logger.info("visit_body_received ip=${metadata.ip} bytes=${rawBody.length}")

        val payload = try {
            json.decodeFromString<VisitPayload>(rawBody)
        } catch (e: SerializationException) {
            logger.warning("visit_parse_failed ip=${metadata.ip} error=${e.message} body=$rawBody")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "invalid_json")
            return
        }

        logger.info(
            "visit_parsed ip=${metadata.ip} path=${payload.path} " +
            "session=${payload.sessionId} lat=${payload.latitude} lon=${payload.longitude}"
        )

        try {
            VisitValidator.validate(payload)
        } catch (validationError: VisitValidationException) {
            logger.warning("visit_validation_failed ip=${metadata.ip} error=${validationError.message}")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_BAD_REQUEST, validationError.message ?: "invalid_payload")
            return
        }

        if (!deduper.shouldProcess(payload.sessionId, payload.path)) {
            logger.info("visit_suppressed ip=${metadata.ip} path=${payload.path} session=${payload.sessionId}")
            withCors(exchange.responseHeaders, origin)
            respond(exchange, HttpURLConnection.HTTP_ACCEPTED, "duplicate_suppressed")
            return
        }

        val visit = AcceptedVisit(
            payload = payload,
            requestMetadata = metadata,
            receivedAtIso = Instant.now(clock).toString(),
        )

        // --- Step 1: Send email (true failure = release dedup + 500) ---
        val emailSent = try {
            emailSender.sendVisitNotification(visit)
            logger.info(
                "visit_email_sent ip=${metadata.ip} path=${payload.path} " +
                "has_location=${payload.latitude != null}"
            )
            true
        } catch (sendError: Exception) {
            deduper.release(payload.sessionId, payload.path)
            logger.log(
                Level.SEVERE,
                "visit_email_failed ip=${metadata.ip} path=${payload.path} error=${sendError.message}",
                sendError
            )
            withCors(exchange.responseHeaders, origin)
            safeRespond(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "send_failed", metadata.ip)
            false
        }

        if (!emailSent) return

        // --- Step 2: Write HTTP response (broken pipe here is NOT a real failure) ---
        logger.info("visit_accepted ip=${metadata.ip} path=${payload.path} has_location=${payload.latitude != null}")
        withCors(exchange.responseHeaders, origin)
        safeRespond(exchange, HttpURLConnection.HTTP_ACCEPTED, "accepted", metadata.ip)
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
            if (total > maxBytes) throw PayloadTooLargeException()
            output.write(buffer, 0, read)
        }
        return output.toString(StandardCharsets.UTF_8)
    }

    /**
     * Writes the HTTP response, swallowing broken-pipe / client-disconnect IOExceptions.
     * These happen when the browser timed out (e.g. Render cold start) but the email
     * was already sent — they are NOT real failures and must not trigger dedup release.
     */
    private fun safeRespond(
        exchange: HttpExchange,
        status: Int,
        body: String,
        ip: String,
        contentType: String = "text/plain; charset=utf-8",
    ) {
        try {
            respond(exchange, status, body, contentType)
            logger.info("visit_response_sent ip=$ip status=$status")
        } catch (e: IOException) {
            // Client disconnected (e.g. cold-start timeout) — email was already sent, this is benign
            logger.warning("visit_response_broken_pipe ip=$ip status=$status — client disconnected, email was already delivered")
        } catch (e: Exception) {
            logger.warning("visit_response_error ip=$ip status=$status error=${e.message}")
        }
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
