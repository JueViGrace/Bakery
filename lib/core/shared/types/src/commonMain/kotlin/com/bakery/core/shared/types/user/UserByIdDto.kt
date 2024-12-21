package com.bakery.core.shared.types.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserByIdDto(
    @SerialName("id")
    val id: String
)
