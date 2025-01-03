package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailsDto(
    @SerialName("order_id")
    val orderId: String,
    @SerialName("product_id")
    val productId: String,
    @SerialName("total_price")
    val totalPrice: Double,
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
    @SerialName("images")
    val images: List<String>,
)
