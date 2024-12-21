package com.bakery.core.types

enum class JwtAuthName(val value: String) {
    SESSION("session-auth"),
    USER("user-auth"),
    ORDER("order-auth"),
    ADMIN("admin-auth")
}
