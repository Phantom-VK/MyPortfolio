package com.vikramaditya.portfolio.visitnotifier

import kotlinx.serialization.Serializable

@Serializable
data class VisitPayload(
    val path: String,
    val referrer: String? = null,
    val userAgent: String? = null,
    val language: String? = null,
    val viewportWidth: Int? = null,
    val viewportHeight: Int? = null,
    val timezone: String? = null,
    val screenWidth: Int? = null,
    val screenHeight: Int? = null,
    val colorMode: String? = null,
    val sessionId: String,
)

data class RequestMetadata(
    val ip: String,
    val userAgentHeader: String?,
    val origin: String?,
    val host: String?,
    val forwardedFor: String?,
    val realIp: String?,
)

data class AcceptedVisit(
    val payload: VisitPayload,
    val requestMetadata: RequestMetadata,
    val receivedAtIso: String,
)
