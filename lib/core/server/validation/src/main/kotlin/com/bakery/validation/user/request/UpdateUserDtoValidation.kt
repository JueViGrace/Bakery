package com.bakery.validation.user.request

import com.bakery.core.shared.types.user.UpdateUserDto
import com.bakery.core.util.Util.validUuid
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateUpdateUserDto() {
    validate<UpdateUserDto> { dto ->
        when {
            !validUuid(dto.id) -> ValidationResult.Invalid("Invalid id.")
            dto.firstName.isEmpty() -> ValidationResult.Invalid("First name cannot be empty")
            dto.lastName.isEmpty() -> ValidationResult.Invalid("Last name cannot be empty")
            dto.phoneNumber.isEmpty() -> ValidationResult.Invalid("Phone number cannot be empty")
            dto.birthDate.isEmpty() -> ValidationResult.Invalid("Birth date cannot be empty")
            dto.address1.isEmpty() -> ValidationResult.Invalid("Address 1 cannot be empty")
            dto.address2.isEmpty() -> ValidationResult.Invalid("Address 2 cannot be empty")
            dto.gender.isEmpty() -> ValidationResult.Invalid("Gender cannot be empty")
            dto.gender != "male" && dto.gender != "female" ->
                ValidationResult.Invalid("Gender must be either male or female")
            else -> ValidationResult.Valid
        }
    }
}
