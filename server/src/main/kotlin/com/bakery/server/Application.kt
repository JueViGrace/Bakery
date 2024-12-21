package com.bakery.server

import com.bakery.server.config.configureHTTP
import com.bakery.server.config.configureKoin
import com.bakery.server.config.configureMonitoring
import com.bakery.server.config.configureRoutes
import com.bakery.server.config.configureRouting
import com.bakery.server.config.configureSecurity
import com.bakery.server.config.configureSerialization
import com.bakery.server.config.configureTemplating
import com.bakery.server.config.configureValidation
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    setupDotenv()
    EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureValidation()
    configureTemplating()
    configureRoutes()
}

fun setupDotenv() {
    val dotenv = dotenv {
        directory = "./src/main/resources"
        filename = ".env"
        ignoreIfMissing = true
    }

    dotenv.entries().forEach { entry ->
        System.setProperty(entry.key, entry.value)
    }
}
