package com.vikramaditya.portfolio.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import kotlin.coroutines.resume
import kotlin.random.Random

private const val VisitFlagKey = "visit_notified_v1"
private const val VisitSessionIdKey = "visit_session_id_v1"

@Composable
fun VisitReporter(colorMode: ColorMode) {
    LaunchedEffect(colorMode) {
        runCatching {
            val storage = window.sessionStorage
            if (storage.getItem(VisitFlagKey) != null) {
                console.log("[VisitReporter] already reported this session, skipping")
                return@runCatching
            }

            val apiBaseUrl = resolveApiBaseUrl()
            if (apiBaseUrl == null) {
                console.warn("[VisitReporter] no API base URL resolved, aborting")
                return@runCatching
            }

            val sessionId = storage.getItem(VisitSessionIdKey) ?: generateSessionId().also {
                storage.setItem(VisitSessionIdKey, it)
            }

            console.log("[VisitReporter] requesting geolocation...")
            val geoResult = requestGeolocation()
            if (geoResult != null) {
                console.log("[VisitReporter] geolocation granted: lat=${geoResult.latitude} lon=${geoResult.longitude} acc=${geoResult.accuracy}m")
            } else {
                console.log("[VisitReporter] geolocation not available or denied, proceeding without location")
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
            payload.latitude = geoResult?.latitude
            payload.longitude = geoResult?.longitude
            payload.locationAccuracy = geoResult?.accuracy

            val jsonBody = js("JSON.stringify(payload)") as String
            console.log("[VisitReporter] sending payload to $apiBaseUrl/api/visit")
            console.log("[VisitReporter] payload: $jsonBody")

            val response = window.fetch(
                input = "$apiBaseUrl/api/visit",
                init = RequestInit(
                    method = "POST",
                    headers = Headers().apply { append("Content-Type", "application/json") },
                    body = jsonBody,
                )
            ).await()

            console.log("[VisitReporter] server responded: ${response.status} ${response.statusText}")
            if (response.ok) {
                // Only mark as reported after a confirmed successful response
                storage.setItem(VisitFlagKey, "1")
            } else {
                val body = response.text().await()
                console.warn("[VisitReporter] non-OK response body: $body")
            }
        }.onFailure { err ->
            console.error("[VisitReporter] uncaught error: ${err.message}")
        }
    }
}

private data class GeoResult(val latitude: Double, val longitude: Double, val accuracy: Double)

/**
 * Requests geolocation with a consent prompt.
 * Returns null silently if denied, unavailable, or timed out.
 * Uses a single getCurrentPosition call with a window callback to bridge JS -> coroutine.
 */
private suspend fun requestGeolocation(): GeoResult? {
    return suspendCancellableCoroutine { cont ->
        // Check API availability first
        val geoAvailable = js("typeof navigator !== 'undefined' && !!navigator.geolocation") as Boolean
        if (!geoAvailable) {
            console.log("[VisitReporter] geolocation API not available")
            cont.resume(null)
            return@suspendCancellableCoroutine
        }

        // Generate a unique callback name to avoid conflicts
        val cbName = "_vr_geo_cb_${(1..8).joinToString("") { ('a'..'z').random().toString() }}"

        var resumed = false
        fun resumeOnce(result: GeoResult?) {
            if (!resumed) {
                resumed = true
                js("delete window[cbName]")
                cont.resume(result)
            }
        }

        window.asDynamic()[cbName] = { lat: Double?, lon: Double?, acc: Double? ->
            if (lat != null && lon != null && acc != null) {
                resumeOnce(GeoResult(lat, lon, acc))
            } else {
                resumeOnce(null)
            }
        }

        runCatching {
            js("""
                (function(name) {
                    navigator.geolocation.getCurrentPosition(
                        function(pos) {
                            window[name](
                                pos.coords.latitude,
                                pos.coords.longitude,
                                pos.coords.accuracy
                            );
                        },
                        function(err) {
                            console.warn('[VisitReporter] geolocation error code=' + err.code + ' msg=' + err.message);
                            window[name](null, null, null);
                        },
                        { enableHighAccuracy: true, timeout: 8000, maximumAge: 0 }
                    );
                })(cbName);
            """)
        }.onFailure { err ->
            console.error("[VisitReporter] getCurrentPosition threw: ${err.message}")
            resumeOnce(null)
        }

        cont.invokeOnCancellation {
            if (!resumed) {
                resumed = true
                js("delete window[cbName]")
            }
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
    val cryptoUuid = runCatching {
        js("window.crypto && window.crypto.randomUUID ? window.crypto.randomUUID() : null") as String?
    }.getOrNull()
    if (!cryptoUuid.isNullOrBlank()) return cryptoUuid
    val randomPart = (1..4).joinToString("") { Random.nextInt(0, 65536).toString(16).padStart(4, '0') }
    return "session-$randomPart"
}
