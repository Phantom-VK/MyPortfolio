package com.vikramaditya.portfolio.visitnotifier

fun main() {
    val config = AppConfig.fromEnvironment()
    val server = VisitServer(config, SmtpEmailSender(config)).start()
    println("Visit notifier listening on port ${server.address.port}")
}
