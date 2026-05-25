package com.vikramaditya.portfolio.visitnotifier

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.add
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger

/**
 * Sends visit notification emails via the Resend HTTP API (https://resend.com).
 * Uses port 443 (HTTPS) — works on Render free tier unlike SMTP port 587 which is blocked.
 *
 * Required env var: RESEND_API_KEY
 * Optional env vars (falls back to AppConfig SMTP fields for from/to):
 *   VISIT_NOTIFY_FROM  — must be a verified sender domain in Resend
 *   VISIT_NOTIFY_TO    — recipient email
 */
class ResendEmailSender(
    private val config: AppConfig,
) : EmailSender {

    private val logger = Logger.getLogger(ResendEmailSender::class.java.name)
    private val json = Json { encodeDefaults = true }

    companion object {
        private const val RESEND_API_URL = "https://api.resend.com/emails"
        private const val CONNECT_TIMEOUT_MS = 5_000
        private const val READ_TIMEOUT_MS = 10_000
    }

    override fun sendVisitNotification(visit: AcceptedVisit) {
        val apiKey = config.resendApiKey

        val requestBody = buildJsonObject {
            put("from", config.visitNotifyFrom)
            putJsonArray("to") { add(config.visitNotifyTo) }
            put("subject", "Portfolio visit: ${visit.payload.path}")
            put("text", buildBody(visit))
        }

        val bodyBytes = json.encodeToString(requestBody).toByteArray(Charsets.UTF_8)

        logger.info("resend_attempt to=${config.visitNotifyTo} path=${visit.payload.path}")

        val url = URL(RESEND_API_URL)
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = CONNECT_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            doOutput = true
            setRequestProperty("Authorization", "Bearer $apiKey")
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
        }

        try {
            connection.outputStream.use { it.write(bodyBytes) }

            val status = connection.responseCode
            val responseBody = runCatching {
                if (status in 200..299) {
                    connection.inputStream.bufferedReader().readText()
                } else {
                    connection.errorStream?.bufferedReader()?.readText() ?: "(no error body)"
                }
            }.getOrElse { "(could not read response body)" }

            if (status in 200..299) {
                logger.info("resend_success status=$status path=${visit.payload.path} response=$responseBody")
            } else {
                logger.severe("resend_failed status=$status path=${visit.payload.path} response=$responseBody")
                throw RuntimeException("Resend API returned HTTP $status: $responseBody")
            }
        } finally {
            connection.disconnect()
        }
    }

    private fun buildBody(visit: AcceptedVisit): String {
        val payload = visit.payload
        val metadata = visit.requestMetadata
        val viewport = "${payload.viewportWidth ?: "unknown"} x ${payload.viewportHeight ?: "unknown"}"
        val screen = "${payload.screenWidth ?: "unknown"} x ${payload.screenHeight ?: "unknown"}"

        return """
            A portfolio visit was recorded.

            Timestamp  : ${visit.receivedAtIso}
            IP         : ${metadata.ip}
            Path       : ${payload.path}
            Referrer   : ${payload.referrer ?: "(direct)"}
            User-Agent : ${payload.userAgent ?: metadata.userAgentHeader ?: "(unknown)"}
            Language   : ${payload.language ?: "(unknown)"}
            Timezone   : ${payload.timezone ?: "(unknown)"}
            Viewport   : $viewport
            Screen     : $screen
            Color mode : ${payload.colorMode ?: "(unknown)"}
            Session ID : ${payload.sessionId}
            Origin     : ${metadata.origin ?: "(unknown)"}
            X-Fwd-For  : ${metadata.forwardedFor ?: "(none)"}
        """.trimIndent()
    }
}
