package com.vikramaditya.portfolio.visitnotifier

import com.sun.net.httpserver.HttpServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.util.concurrent.CopyOnWriteArrayList

class VisitServerTest {
    private lateinit var server: HttpServer
    private lateinit var emailSender: FakeEmailSender
    private lateinit var baseUrl: String

    @BeforeEach
    fun setUp() {
        emailSender = FakeEmailSender()
        val config = AppConfig(
            port = 0,
            allowedOrigins = setOf("http://localhost:8080"),
            resendApiKey = "test-resend-key",
            visitNotifyTo = "to@example.com",
            visitNotifyFrom = "from@example.com",
            rateLimitWindowSeconds = 60,
            rateLimitMaxRequests = 2,
            dedupeTtlSeconds = 900,
            maxPayloadBytes = 2_048,
        )
        server = VisitServer(
            config = config,
            emailSender = emailSender,
            clock = Clock.fixed(Instant.parse("2026-05-25T00:00:00Z"), ZoneOffset.UTC),
        ).start()
        baseUrl = "http://127.0.0.1:${server.address.port}"
    }

    @AfterEach
    fun tearDown() {
        server.stop(0)
    }

    @Test
    fun `valid payload returns accepted and sends email`() {
        val response = post(validPayload())

        assertEquals(HttpURLConnection.HTTP_ACCEPTED, response.statusCode())
        assertEquals(1, emailSender.visits.size)
        assertEquals("203.0.113.5", emailSender.visits.first().requestMetadata.ip)
    }

    @Test
    fun `malformed json returns bad request`() {
        val response = post("{not-json")

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode())
        assertEquals(0, emailSender.visits.size)
    }

    @Test
    fun `oversized fields return bad request`() {
        val largePath = "/" + "a".repeat(600)
        val response = post(validPayload(path = largePath))

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode())
        assertEquals(0, emailSender.visits.size)
    }

    @Test
    fun `repeated calls inside ttl are suppressed`() {
        val first = post(validPayload())
        val second = post(validPayload())

        assertEquals(HttpURLConnection.HTTP_ACCEPTED, first.statusCode())
        assertEquals(HttpURLConnection.HTTP_ACCEPTED, second.statusCode())
        assertEquals(1, emailSender.visits.size)
        assertTrue(second.body().contains("duplicate_suppressed"))
    }

    @Test
    fun `rate limit returns too many requests`() {
        val first = post(validPayload(sessionId = "session-1"))
        val second = post(validPayload(sessionId = "session-2"))
        val third = post(validPayload(sessionId = "session-3"))

        assertEquals(HttpURLConnection.HTTP_ACCEPTED, first.statusCode())
        assertEquals(HttpURLConnection.HTTP_ACCEPTED, second.statusCode())
        assertEquals(429, third.statusCode())
    }

    private fun post(body: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder(URI("$baseUrl/api/visit"))
            .header("Origin", "http://localhost:8080")
            .header("Content-Type", "application/json")
            .header("X-Forwarded-For", "203.0.113.5, 10.0.0.1")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
    }

    private fun validPayload(
        path: String = "/",
        sessionId: String = "session-1234",
    ): String {
        return """
            {
              "path": "$path",
              "referrer": "https://google.com/",
              "userAgent": "ExampleBrowser",
              "language": "en-US",
              "viewportWidth": 1440,
              "viewportHeight": 900,
              "timezone": "Asia/Kolkata",
              "screenWidth": 1440,
              "screenHeight": 900,
              "colorMode": "dark",
              "sessionId": "$sessionId"
            }
        """.trimIndent()
    }

    private class FakeEmailSender : EmailSender {
        val visits = CopyOnWriteArrayList<AcceptedVisit>()

        override fun sendVisitNotification(visit: AcceptedVisit) {
            visits += visit
        }
    }
}
