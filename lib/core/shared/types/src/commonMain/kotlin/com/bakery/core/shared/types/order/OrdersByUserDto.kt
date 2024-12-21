package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrdersByUserDto(
    @SerialName("user_id")
    val userId: String,
)
