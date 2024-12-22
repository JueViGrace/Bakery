package com.bakery.validation.user.request

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.userValidation() {
    validateUpdateUserDto()
}
