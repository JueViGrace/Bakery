package com.bakery.server.config

import com.bakery.validation.serverRequestValidation
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        serverRequestValidation()
    }
}
