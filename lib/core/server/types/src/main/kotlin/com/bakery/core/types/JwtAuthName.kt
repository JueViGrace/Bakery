package com.bakery.core.types

enum class JwtAuthName(val value: String) {
    SESSION("session-auth"),
    ADMIN("admin-auth"),
    USER_ID("user-id-auth"),
    ORDER_ID("order-id-auth"),
}
