package com.bakery.validation.order.request

import com.bakery.core.shared.types.order.OrderStatus
import com.bakery.core.util.Util.validUuid
import com.bakery.core.shared.types.order.UpdateOrderDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateCancelOrderDto() {
    validate<UpdateOrderDto> { dto ->
        when {
            dto.id.isBlank() -> ValidationResult.Invalid("Id cannot be blank")
            !validUuid(dto.id) -> ValidationResult.Invalid("Id is not valid")
            dto.status.isBlank() -> ValidationResult.Invalid("Status cannot be blank")
            OrderStatus.valueOf(dto.status) != OrderStatus.CANCELLED -> ValidationResult.Invalid("Status is not valid")
            else -> ValidationResult.Valid
        }
    }
}
