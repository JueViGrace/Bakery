package com.bakery.core.shared.types.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordDto(
    @SerialName("username")
    val username: String,
)
