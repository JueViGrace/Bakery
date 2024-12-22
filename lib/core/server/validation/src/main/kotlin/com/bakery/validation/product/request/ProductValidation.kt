package com.bakery.validation.product.request

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.productValidation() {
    validateCreateProductDto()
    validateUpdateProductDto()
}
