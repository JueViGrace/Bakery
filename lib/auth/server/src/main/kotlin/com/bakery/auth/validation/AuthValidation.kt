package com.bakery.auth.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.authValidation() {
    validateSignInDto()
    validateSignUpDto()
}

