package com.bakery.validation.order.request

import com.bakery.core.util.Util.validUuid
import com.bakery.core.shared.types.order.CreateOrderDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateCreateOrderDto() {
    validate<CreateOrderDto> { dto ->
        when {
            dto.paymentMethod.isEmpty() -> ValidationResult.Invalid("Payment method cannot be empty")
            dto.userId.isEmpty() -> ValidationResult.Invalid("User id cannot be empty")
            !validUuid(dto.userId) -> ValidationResult.Invalid("User id is not valid")
            dto.details.isEmpty() -> ValidationResult.Invalid("Details cannot be empty")
            else -> {
                return@validate dto.details.map { detail ->
                    if (detail.productId.isEmpty()) {
                        return@map ValidationResult.Invalid("Product id cannot be empty")
                    }
                    if (!validUuid(detail.productId)) {
                        return@map ValidationResult.Invalid("Product id is not valid")
                    }
                    if (detail.quantity < 0) {
                        return@map ValidationResult.Invalid("Quantity must not be negative")
                    }
                    if (detail.name.isEmpty()) {
                        return@map ValidationResult.Invalid("Name cannot be empty")
                    }
                    if (detail.category.isEmpty()) {
                        return@map ValidationResult.Invalid("Category cannot be empty")
                    }
                    if (detail.price < 0) {
                        return@map ValidationResult.Invalid("Price must not be negative")
                    }
                    if (detail.discount < 0) {
                        return@map ValidationResult.Invalid("Discount must not be negative")
                    }
                    if (detail.rating < 0) {
                        return@map ValidationResult.Invalid("Rating must not be negative")
                    }

                    ValidationResult.Valid
                }.firstOrNull { it is ValidationResult.Invalid } ?: ValidationResult.Valid
            }
        }
    }
}
