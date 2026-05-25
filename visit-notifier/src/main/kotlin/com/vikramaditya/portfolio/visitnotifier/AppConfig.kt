package com.vikramaditya.portfolio.visitnotifier

import java.nio.file.Files
import java.nio.file.Path

data class AppConfig(
    val port: Int,
    val allowedOrigins: Set<String>,
    val smtpHost: String,
    val smtpPort: Int,
    val smtpUsername: String,
    val smtpAppPassword: String,
    val visitNotifyTo: String,
    val visitNotifyFrom: String,
    val rateLimitWindowSeconds: Long,
    val rateLimitMaxRequests: Int,
    val dedupeTtlSeconds: Long,
    val maxPayloadBytes: Int,
) {
    companion object {
        fun fromEnvironment(env: Map<String, String> = System.getenv()): AppConfig {
            val mergedEnv = loadDotEnv() + env
            return AppConfig(
                port = mergedEnv["PORT"]?.toIntOrNull() ?: 8787,
                allowedOrigins = mergedEnv.require("ALLOWED_ORIGINS")
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .toSet(),
                smtpHost = mergedEnv["SMTP_HOST"]?.ifBlank { null } ?: "smtp.gmail.com",
                smtpPort = mergedEnv["SMTP_PORT"]?.toIntOrNull() ?: 587,
                smtpUsername = mergedEnv.require("SMTP_USERNAME"),
                smtpAppPassword = mergedEnv.require("SMTP_APP_PASSWORD"),
                visitNotifyTo = mergedEnv.require("VISIT_NOTIFY_TO"),
                visitNotifyFrom = mergedEnv.require("VISIT_NOTIFY_FROM"),
                rateLimitWindowSeconds = mergedEnv["VISIT_RATE_LIMIT_WINDOW_SECONDS"]?.toLongOrNull() ?: 60L,
                rateLimitMaxRequests = mergedEnv["VISIT_RATE_LIMIT_MAX_REQUESTS"]?.toIntOrNull() ?: 30,
                dedupeTtlSeconds = mergedEnv["VISIT_DEDUPE_TTL_SECONDS"]?.toLongOrNull() ?: 900L,
                maxPayloadBytes = mergedEnv["VISIT_MAX_PAYLOAD_BYTES"]?.toIntOrNull() ?: 8_192,
            )
        }

        private fun loadDotEnv(workingDirectory: Path = Path.of(System.getProperty("user.dir"))): Map<String, String> {
            val candidatePaths = listOf(
                workingDirectory.resolve(".env"),
                workingDirectory.resolve("visit-notifier/.env"),
            ).map(Path::normalize).distinct()

            return candidatePaths
                .filter(Files::isRegularFile)
                .fold(emptyMap()) { values, path -> values + parseDotEnv(path) }
        }

        private fun parseDotEnv(path: Path): Map<String, String> {
            return Files.readAllLines(path)
                .map(String::trim)
                .filter { it.isNotEmpty() && !it.startsWith("#") }
                .mapNotNull { line ->
                    val separatorIndex = line.indexOf('=')
                    if (separatorIndex <= 0) return@mapNotNull null

                    val key = line.substring(0, separatorIndex).trim()
                    if (key.isEmpty()) return@mapNotNull null

                    val rawValue = line.substring(separatorIndex + 1).trim()
                    key to rawValue.removeWrappingQuotes()
                }
                .toMap()
        }

        private fun String.removeWrappingQuotes(): String {
            if (length >= 2 && first() == last() && (first() == '"' || first() == '\'')) {
                return substring(1, length - 1)
            }
            return this
        }

        private fun Map<String, String>.require(key: String): String =
            this[key]?.takeIf { it.isNotBlank() }
                ?: error("Missing required environment variable: $key")
    }
}
