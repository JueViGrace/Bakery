package com.bakery.core.types.auth

import com.bakery.core.types.user.User

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
    val active: Boolean = false,
)
