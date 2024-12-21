package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrderDto(
    @SerialName("id")
    val id: String,
    @SerialName("status")
    val status: String
)
