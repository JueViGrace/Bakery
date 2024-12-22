package com.bakery.validation.order.request

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.orderValidation() {
    validateCreateOrderDto()
    validateUpdateOrderDto()
    validateCancelOrderDto()
}
