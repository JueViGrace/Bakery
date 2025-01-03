package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelOrderDto(
    @SerialName("id")
    val id: String,
)
