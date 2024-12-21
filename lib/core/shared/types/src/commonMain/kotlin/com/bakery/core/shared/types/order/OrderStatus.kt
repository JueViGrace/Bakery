package com.bakery.core.shared.types.order

enum class OrderStatus(val value: String) {
    Placed("placed"),
    Cancelled("cancelled"),
    Dispatched("dispatched"),
    Delivered("delivered")
}
