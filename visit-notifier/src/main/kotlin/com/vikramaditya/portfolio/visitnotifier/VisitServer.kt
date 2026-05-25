package com.vikramaditya.portfolio.visitnotifier

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.time.Clock
import java.util.concurrent.Executors

class VisitServer(
    private val config: AppConfig,
    emailSender: EmailSender,
    clock: Clock = Clock.systemUTC(),
) {
    private val handler = VisitHandler(
        config = config,
        emailSender = emailSender,
        deduper = VisitDeduper(config.dedupeTtlSeconds, clock),
        rateLimiter = RateLimiter(config.rateLimitMaxRequests, config.rateLimitWindowSeconds, clock),
        clock = clock,
    )

    fun start(): HttpServer {
        val server = HttpServer.create(InetSocketAddress(config.port), 0)
        server.createContext("/health") { exchange -> handler.handleHealth(exchange) }
        server.createContext("/api/visit") { exchange -> handler.handleVisit(exchange) }
        server.executor = Executors.newFixedThreadPool(4)
        server.start()
        return server
    }
}
