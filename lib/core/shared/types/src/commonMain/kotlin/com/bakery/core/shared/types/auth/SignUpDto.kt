package com.bakery.core.shared.types.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("address")
    val address1: String,
    @SerialName("address2")
    val address2: String,
    @SerialName("gender")
    val gender: String,
)
