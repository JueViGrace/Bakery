package com.bakery.core.types.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("address1")
    val address1: String,
    @SerialName("address2")
    val address2: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("createdAt")
    val createdAt: String,
)
