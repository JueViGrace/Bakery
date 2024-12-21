package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderDetailsDto(
    @SerialName("product_id")
    val productId: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("price")
    val price: Double,
    @SerialName("discount")
    val discount: Double,
    @SerialName("rating")
    val rating: Double,
)
