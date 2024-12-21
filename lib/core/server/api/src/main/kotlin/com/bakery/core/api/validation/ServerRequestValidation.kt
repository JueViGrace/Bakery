package com.bakery.core.api.validation

import com.bakery.auth.validation.authValidation
import com.bakery.order.validation.orderValidation
import com.bakery.product.validation.productValidation
import com.bakery.user.validation.userValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.serverRequestValidation() {
    authValidation()
    userValidation()
    productValidation()
    orderValidation()
}
