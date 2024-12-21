package com.bakery.user.validation

import com.bakery.core.util.Util.validUuid
import com.bakery.core.shared.types.user.UserByIdDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateUserByIdDto() {
    validate<UserByIdDto> { dto ->
        when {
            dto.id.isEmpty() -> ValidationResult.Invalid("Id must not be empty.")
            !validUuid(dto.id) -> ValidationResult.Invalid("Invalid id.")
            else -> ValidationResult.Valid
        }
    }
}
