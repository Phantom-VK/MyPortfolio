package com.vikramaditya.portfolio.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import kotlin.random.Random

private const val VisitFlagKey = "visit_notified_v1"
private const val VisitSessionIdKey = "visit_session_id_v1"

@Composable
fun VisitReporter(colorMode: ColorMode) {
    LaunchedEffect(colorMode) {
        runCatching {
            val storage = window.sessionStorage
            if (storage.getItem(VisitFlagKey) != null) return@runCatching

            val apiBaseUrl = resolveApiBaseUrl() ?: return@runCatching
            val sessionId = storage.getItem(VisitSessionIdKey) ?: generateSessionId().also {
                storage.setItem(VisitSessionIdKey, it)
            }

            val payload = js("({})")
            payload.path = window.location.pathname
            payload.referrer = document.referrer.takeIf { it.isNotBlank() }
            payload.userAgent = js("window.navigator.userAgent")
            payload.language = js("window.navigator.language")
            payload.viewportWidth = window.innerWidth
            payload.viewportHeight = window.innerHeight
            payload.timezone = resolveTimezone()
            payload.screenWidth = window.screen.width
            payload.screenHeight = window.screen.height
            payload.colorMode = colorMode.name.lowercase()
            payload.sessionId = sessionId

            storage.setItem(VisitFlagKey, "1")

            window.fetch(
                input = "$apiBaseUrl/api/visit",
                init = RequestInit(
                    method = "POST",
                    headers = Headers().apply { append("Content-Type", "application/json") },
                    body = js("JSON.stringify(payload)") as String,
                )
            )
        }
    }
}

private fun resolveApiBaseUrl(): String? {
    val configured = document
        .querySelector("meta[name='visit-notify-api-base-url']")
        ?.getAttribute("content")
        ?.trim()
        ?.removeSuffix("/")
    if (!configured.isNullOrEmpty()) return configured

    return when (window.location.hostname) {
        "localhost", "127.0.0.1" -> "http://localhost:8787"
        else -> null
    }
}

private fun resolveTimezone(): String? {
    return runCatching {
        js("Intl.DateTimeFormat().resolvedOptions().timeZone") as String
    }.getOrNull()
}

private fun generateSessionId(): String {
    val cryptoUuid = runCatching { js("window.crypto && window.crypto.randomUUID ? window.crypto.randomUUID() : null") as String? }
        .getOrNull()
    if (!cryptoUuid.isNullOrBlank()) return cryptoUuid
    val randomPart = (1..4).joinToString("") { Random.nextInt(0, 65536).toString(16).padStart(4, '0') }
    return "session-$randomPart"
}
