package com.bakery.core.types

data class UserIdValidation(
    val role: Role,
    val userId: String,
    val username: String,
)
