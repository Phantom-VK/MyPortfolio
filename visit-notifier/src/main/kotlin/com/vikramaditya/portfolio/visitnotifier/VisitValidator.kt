package com.vikramaditya.portfolio.visitnotifier

class VisitValidationException(message: String) : IllegalArgumentException(message)

object VisitValidator {
    fun validate(payload: VisitPayload) {
        requireLength(payload.path, "path", 1, 512)
        requireStartsWithSlash(payload.path)
        requireLength(payload.referrer, "referrer", 0, 2_048)
        requireLength(payload.userAgent, "userAgent", 0, 2_048)
        requireLength(payload.language, "language", 0, 64)
        requireLength(payload.timezone, "timezone", 0, 64)
        requireLength(payload.colorMode, "colorMode", 0, 16)
        requireLength(payload.sessionId, "sessionId", 8, 128)
        requireDimension(payload.viewportWidth, "viewportWidth")
        requireDimension(payload.viewportHeight, "viewportHeight")
        requireDimension(payload.screenWidth, "screenWidth")
        requireDimension(payload.screenHeight, "screenHeight")
    }

    private fun requireStartsWithSlash(path: String) {
        if (!path.startsWith("/")) {
            throw VisitValidationException("path must start with '/'")
        }
    }

    private fun requireLength(value: String?, field: String, min: Int, max: Int) {
        if (value == null) return
        if (value.length !in min..max) {
            throw VisitValidationException("$field length must be between $min and $max")
        }
    }

    private fun requireDimension(value: Int?, field: String) {
        if (value == null) return
        if (value !in 0..20_000) {
            throw VisitValidationException("$field must be between 0 and 20000")
        }
    }
}
