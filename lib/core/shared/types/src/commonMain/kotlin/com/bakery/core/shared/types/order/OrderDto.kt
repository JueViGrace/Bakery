package com.bakery.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id")
    val id: String,
    @SerialName("total_amount")
    val totalAmount: Double,
    @SerialName("payment_method")
    val paymentMethod: String,
    @SerialName("status")
    val status: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("details")
    val details: List<OrderDetailsDto>
)
