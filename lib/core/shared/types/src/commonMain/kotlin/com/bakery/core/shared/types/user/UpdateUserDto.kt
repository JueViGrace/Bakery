package com.bakery.core.shared.types.user

import kotlinx.serialization.SerialName

data class UpdateUserDto(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
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
)
