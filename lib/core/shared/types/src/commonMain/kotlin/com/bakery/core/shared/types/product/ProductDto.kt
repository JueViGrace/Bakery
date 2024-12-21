package com.bakery.core.shared.types.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("category")
    val category: String,
    @SerialName("price")
    val price: Double,
    @SerialName("stock")
    val stock: Int,
    @SerialName("issued")
    val issued: Int,
    @SerialName("hasStock")
    val hasStock: Boolean,
    @SerialName("discount")
    val discount: Double,
    @SerialName("rating")
    val rating: Double,
    @SerialName("images")
    val images: List<String>,
)
