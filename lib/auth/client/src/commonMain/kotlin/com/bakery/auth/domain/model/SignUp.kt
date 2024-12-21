package com.bakery.auth.domain.model

data class SignUp(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val birthDate: String,
    val address1: String,
    val address2: String,
    val gender: String,
)
