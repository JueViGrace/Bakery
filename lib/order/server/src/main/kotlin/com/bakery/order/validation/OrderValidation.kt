package com.bakery.order.validation

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.orderValidation() {
    validateCreateOrderDto()
    validateUpdateOrderDto()
    validateCancelOrderDto()
    validateOrderById()
    validateOrderByUser()
}
