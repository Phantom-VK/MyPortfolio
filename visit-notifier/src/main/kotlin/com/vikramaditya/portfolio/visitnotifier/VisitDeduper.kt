package com.vikramaditya.portfolio.visitnotifier

import java.time.Clock

class VisitDeduper(
    private val ttlSeconds: Long,
    private val clock: Clock,
) {
    private val seen = linkedMapOf<String, Long>()

    @Synchronized
    fun shouldProcess(sessionId: String, path: String): Boolean {
        val now = clock.instant().epochSecond
        prune(now)
        val key = "$sessionId::$path"
        val previous = seen[key]
        if (previous != null && now - previous < ttlSeconds) {
            return false
        }
        seen[key] = now
        return true
    }

    @Synchronized
    fun release(sessionId: String, path: String) {
        seen.remove("$sessionId::$path")
    }

    private fun prune(nowEpochSeconds: Long) {
        val iterator = seen.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (nowEpochSeconds - entry.value >= ttlSeconds) {
                iterator.remove()
            }
        }
    }
}
