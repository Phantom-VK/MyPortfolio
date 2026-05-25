package com.vikramaditya.portfolio.visitnotifier

import java.util.logging.Logger

private val logger = Logger.getLogger("com.vikramaditya.portfolio.visitnotifier.Main")

fun main() {
    logger.info("visit_notifier_starting")
    val config = AppConfig.fromEnvironment()
    val emailSender = ResendEmailSender(config)
    val server = VisitServer(config, emailSender).start()
    logger.info("visit_notifier_started port=${server.address.port}")
}
