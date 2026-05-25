package com.vikramaditya.portfolio.visitnotifier

import java.time.Clock
import kotlin.math.max

class RateLimiter(
    private val maxRequests: Int,
    private val windowSeconds: Long,
    private val clock: Clock,
) {
    private val windows = mutableMapOf<String, Window>()

    @Synchronized
    fun allow(key: String): Boolean {
        val now = clock.instant().epochSecond
        val current = windows[key]
        if (current == null || now - current.windowStartedAt >= windowSeconds) {
            windows[key] = Window(now, 1)
            prune(now)
            return true
        }

        if (current.requestCount >= maxRequests) {
            prune(now)
            return false
        }

        windows[key] = current.copy(requestCount = current.requestCount + 1)
        prune(now)
        return true
    }

    private fun prune(now: Long) {
        val threshold = now - max(windowSeconds, 1)
        windows.entries.removeIf { (_, value) -> value.windowStartedAt < threshold }
    }

    private data class Window(
        val windowStartedAt: Long,
        val requestCount: Int,
    )
}
