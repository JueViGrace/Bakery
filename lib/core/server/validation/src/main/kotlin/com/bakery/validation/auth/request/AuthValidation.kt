package com.bakery.validation.auth.request

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.authValidation() {
    validateSignInDto()
    validateSignUpDto()
}

