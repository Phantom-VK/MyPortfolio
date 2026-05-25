package com.vikramaditya.portfolio.visitnotifier

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

class SmtpEmailSender(
    private val config: AppConfig,
) : EmailSender {
    private val session: Session by lazy {
        val properties = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", config.smtpHost)
            put("mail.smtp.port", config.smtpPort.toString())
        }

        Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(config.smtpUsername, config.smtpAppPassword)
            }
        })
    }

    override fun sendVisitNotification(visit: AcceptedVisit) {
        val message = MimeMessage(session).apply {
            setFrom(InternetAddress(config.visitNotifyFrom))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.visitNotifyTo))
            subject = "Portfolio visit: ${visit.payload.path}"
            setText(buildBody(visit))
        }

        Transport.send(message)
    }

    private fun buildBody(visit: AcceptedVisit): String {
        val payload = visit.payload
        val metadata = visit.requestMetadata
        val viewport = "${payload.viewportWidth ?: "unknown"} x ${payload.viewportHeight ?: "unknown"}"
        val screen = "${payload.screenWidth ?: "unknown"} x ${payload.screenHeight ?: "unknown"}"

        return """
            A portfolio visit was recorded.

            Timestamp: ${visit.receivedAtIso}
            IP: ${metadata.ip}
            Path: ${payload.path}
            Referrer: ${payload.referrer ?: "(none)"}
            User-Agent: ${payload.userAgent ?: metadata.userAgentHeader ?: "(unknown)"}
            Language: ${payload.language ?: "(unknown)"}
            Timezone: ${payload.timezone ?: "(unknown)"}
            Viewport: $viewport
            Screen: $screen
            Color mode: ${payload.colorMode ?: "(unknown)"}
            Session ID: ${payload.sessionId}
            Origin: ${metadata.origin ?: "(unknown)"}
            Host: ${metadata.host ?: "(unknown)"}
            X-Forwarded-For: ${metadata.forwardedFor ?: "(none)"}
            Real-IP Header: ${metadata.realIp ?: "(none)"}
        """.trimIndent()
    }
}
