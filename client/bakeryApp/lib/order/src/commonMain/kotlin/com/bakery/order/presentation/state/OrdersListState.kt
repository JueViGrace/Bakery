package com.bakery.order.presentation.state

import com.bakery.core.types.order.Order

data class OrdersListState(
    val orders: List<Order> = emptyList(),
)
