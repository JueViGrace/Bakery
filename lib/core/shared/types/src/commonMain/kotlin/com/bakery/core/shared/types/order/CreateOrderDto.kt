package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderDto(
    @SerialName("payment_method")
    val paymentMethod: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("details")
    val details: List<CreateOrderDetailsDto>,
)
