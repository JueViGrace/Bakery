package com.bakery.validation.product.request

import com.bakery.core.shared.types.product.UpdateProductDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateUpdateProductDto() {
    validate<UpdateProductDto> { dto ->
        when {
            dto.id.isBlank() -> ValidationResult.Invalid("Id must not be empty")
            dto.name.isBlank() -> ValidationResult.Invalid("Name must not be empty")
            dto.description.isBlank() -> ValidationResult.Invalid("Description must not be empty")
            dto.category.isBlank() -> ValidationResult.Invalid("Category must not be empty")
            dto.price.isNaN() -> ValidationResult.Invalid("Price must not be empty")
            dto.price < 0.0 -> ValidationResult.Invalid("Price must not be negative")
            dto.stock < 0 -> ValidationResult.Invalid("Stock must not be negative")
            dto.discount.isNaN() -> ValidationResult.Invalid("Discount must not be empty")
            dto.discount < 0.0 -> ValidationResult.Invalid("Discount must not be negative")
            !dto.hasStock && dto.stock != 0 -> ValidationResult.Invalid("Stock must be 0 if hasStock is false")
            !dto.hasStock && dto.discount != 0.0 -> ValidationResult.Invalid("Discount must be 0 if hasStock is false")
            dto.rating.isNaN() -> ValidationResult.Invalid("Rating must not be empty")
            dto.rating < 0.0 -> ValidationResult.Invalid("Rating must not be negative")
            else -> ValidationResult.Valid
        }
    }
}
