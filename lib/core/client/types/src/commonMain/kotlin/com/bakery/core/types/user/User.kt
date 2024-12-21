package com.bakery.core.types.user

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val birthDate: String,
    val address1: String,
    val address2: String,
    val gender: String,
    val createdAt: String,
)
