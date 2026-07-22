package com.vikramaditya.portfolio.visitnotifier

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files

class AppConfigTest {
    @Test
    fun `from environment loads values from dot env file`() {
        val workingDirectory = Files.createTempDirectory("visit-notifier-config-test")
        Files.writeString(
            workingDirectory.resolve(".env"),
            """
            ALLOWED_ORIGINS=http://localhost:8080
            RESEND_API_KEY=test-resend-key
            VISIT_NOTIFY_TO=to@example.com
            VISIT_NOTIFY_FROM=from@example.com
            """.trimIndent(),
        )

        val config = withWorkingDirectory(workingDirectory.toString()) {
            AppConfig.fromEnvironment(emptyMap())
        }

        assertEquals(setOf("http://localhost:8080"), config.allowedOrigins)
        assertEquals("test-resend-key", config.resendApiKey)
        assertEquals("to@example.com", config.visitNotifyTo)
        assertEquals("from@example.com", config.visitNotifyFrom)
    }

    @Test
    fun `system environment overrides dot env values`() {
        val workingDirectory = Files.createTempDirectory("visit-notifier-config-override-test")
        Files.writeString(
            workingDirectory.resolve(".env"),
            """
            ALLOWED_ORIGINS=http://localhost:8080
            RESEND_API_KEY=file-resend-key
            VISIT_NOTIFY_TO=file-to@example.com
            VISIT_NOTIFY_FROM=file-from@example.com
            """.trimIndent(),
        )

        val config = withWorkingDirectory(workingDirectory.toString()) {
            AppConfig.fromEnvironment(
                mapOf(
                    "ALLOWED_ORIGINS" to "http://127.0.0.1:8080",
                    "RESEND_API_KEY" to "env-resend-key",
                    "VISIT_NOTIFY_TO" to "env-to@example.com",
                    "VISIT_NOTIFY_FROM" to "env-from@example.com",
                ),
            )
        }

        assertEquals(setOf("http://127.0.0.1:8080"), config.allowedOrigins)
        assertEquals("env-resend-key", config.resendApiKey)
        assertEquals("env-to@example.com", config.visitNotifyTo)
        assertEquals("env-from@example.com", config.visitNotifyFrom)
    }

    @Test
    fun `from environment loads module dot env when started from repo root`() {
        val repoRoot = Files.createTempDirectory("visit-notifier-config-repo-root-test")
        val moduleDirectory = Files.createDirectories(repoRoot.resolve("visit-notifier"))
        Files.writeString(
            moduleDirectory.resolve(".env"),
            """
            ALLOWED_ORIGINS=http://localhost:8080
            RESEND_API_KEY=test-resend-key
            VISIT_NOTIFY_TO=to@example.com
            VISIT_NOTIFY_FROM=from@example.com
            """.trimIndent(),
        )

        val config = withWorkingDirectory(repoRoot.toString()) {
            AppConfig.fromEnvironment(emptyMap())
        }

        assertTrue(config.allowedOrigins.contains("http://localhost:8080"))
        assertEquals("test-resend-key", config.resendApiKey)
    }

    private fun <T> withWorkingDirectory(directory: String, block: () -> T): T {
        val original = System.getProperty("user.dir")
        System.setProperty("user.dir", directory)
        return try {
            block()
        } finally {
            System.setProperty("user.dir", original)
        }
    }
}
