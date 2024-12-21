package com.bakery.server.config

import com.bakery.core.api.routes.serverRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRoutes() {
    routing {
        serverRoutes()
    }
}
