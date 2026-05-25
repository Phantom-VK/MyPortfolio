package com.vikramaditya.portfolio.visitnotifier

interface EmailSender {
    fun sendVisitNotification(visit: AcceptedVisit)
}
