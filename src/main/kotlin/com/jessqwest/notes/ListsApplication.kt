package com.jessqwest.notes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@SpringBootApplication
class ListsApplication

fun main(args: Array<String>) {
    runApplication<ListsApplication>(*args)
}

@Component
class StartupUrlPrinter : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val port = event.applicationContext.environment.getProperty("local.server.port") ?: "8080"
        println("API is running at: http://localhost:$port/lists")
    }
}