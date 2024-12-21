package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderByUserDto(
    @SerialName("order_id")
    val orderId: String,
    @SerialName("user_id")
    val userId: String,
)
