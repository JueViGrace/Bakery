package com.bakery.validation.product.request

import com.bakery.core.shared.types.product.CreateProductDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateCreateProductDto() {
    validate<CreateProductDto> { dto ->
        when {
            dto.name.isBlank() -> ValidationResult.Invalid("Name must not be empty")
            dto.description.isBlank() -> ValidationResult.Invalid("Description must not be empty")
            dto.category.isBlank() -> ValidationResult.Invalid("Category must not be empty")
            dto.price.isNaN() -> ValidationResult.Invalid("Price must not be empty")
            dto.price < 0.0 -> ValidationResult.Invalid("Price must not be negative")
            dto.stock < 0 -> ValidationResult.Invalid("Stock must not be negative")
            !dto.hasStock && dto.stock != 0 -> ValidationResult.Invalid("Stock must be 0 if hasStock is false")
            !dto.hasStock && dto.discount != 0.0 -> ValidationResult.Invalid("Discount must be 0 if hasStock is false")
            dto.discount.isNaN() -> ValidationResult.Invalid("Discount must not be empty")
            dto.discount < 0.0 -> ValidationResult.Invalid("Discount must not be negative")
            else -> ValidationResult.Valid
        }
    }
}
