package com.vikramaditya.portfolio.visitnotifier

import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Logger

data class AppConfig(
    val port: Int,
    val allowedOrigins: Set<String>,
    // Resend HTTP API (replaces SMTP — works on Render free tier)
    val resendApiKey: String,
    // SMTP fields kept for reference / local fallback logging but not used in production
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
        private val logger = Logger.getLogger(AppConfig::class.java.name)

        fun fromEnvironment(env: Map<String, String> = System.getenv()): AppConfig {
            val mergedEnv = loadDotEnv() + env

            logger.info("config_loading port=${mergedEnv["PORT"]} allowed_origins=${mergedEnv["ALLOWED_ORIGINS"]}")

            val config = AppConfig(
                port = mergedEnv["PORT"]?.toIntOrNull() ?: 8787,
                allowedOrigins = mergedEnv.require("ALLOWED_ORIGINS")
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .toSet(),
                resendApiKey = mergedEnv.require("RESEND_API_KEY"),
                smtpHost = mergedEnv["SMTP_HOST"]?.ifBlank { null } ?: "smtp.gmail.com",
                smtpPort = mergedEnv["SMTP_PORT"]?.toIntOrNull() ?: 587,
                smtpUsername = mergedEnv["SMTP_USERNAME"] ?: "",
                smtpAppPassword = mergedEnv["SMTP_APP_PASSWORD"] ?: "",
                visitNotifyTo = mergedEnv.require("VISIT_NOTIFY_TO"),
                visitNotifyFrom = mergedEnv.require("VISIT_NOTIFY_FROM"),
                rateLimitWindowSeconds = mergedEnv["VISIT_RATE_LIMIT_WINDOW_SECONDS"]?.toLongOrNull() ?: 60L,
                rateLimitMaxRequests = mergedEnv["VISIT_RATE_LIMIT_MAX_REQUESTS"]?.toIntOrNull() ?: 30,
                dedupeTtlSeconds = mergedEnv["VISIT_DEDUPE_TTL_SECONDS"]?.toLongOrNull() ?: 900L,
                maxPayloadBytes = mergedEnv["VISIT_MAX_PAYLOAD_BYTES"]?.toIntOrNull() ?: 8_192,
            )

            logger.info(
                "config_loaded port=${config.port} " +
                "allowed_origins=${config.allowedOrigins} " +
                "notify_to=${config.visitNotifyTo} " +
                "notify_from=${config.visitNotifyFrom} " +
                "resend_key_set=${config.resendApiKey.isNotBlank()}"
            )

            return config
        }

        private fun loadDotEnv(workingDirectory: Path = Path.of(System.getProperty("user.dir"))): Map<String, String> {
            val candidatePaths = listOf(
                workingDirectory.resolve(".env"),
                workingDirectory.resolve("visit-notifier/.env"),
            ).map(Path::normalize).distinct()

            return candidatePaths
                .filter(Files::isRegularFile)
                .fold(emptyMap()) { values, path ->
                    logger.info("config_dotenv_loaded path=$path")
                    values + parseDotEnv(path)
                }
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
