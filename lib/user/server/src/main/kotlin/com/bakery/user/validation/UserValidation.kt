package com.bakery.user.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.userValidation() {
    validateUserByIdDto()
    validateUpdateUserDto()
}
