package com.bakery.validation

import com.bakery.validation.auth.request.authValidation
import com.bakery.validation.order.request.orderValidation
import com.bakery.validation.product.request.productValidation
import com.bakery.validation.user.request.userValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.serverRequestValidation() {
    authValidation()
    userValidation()
    productValidation()
    orderValidation()
}
