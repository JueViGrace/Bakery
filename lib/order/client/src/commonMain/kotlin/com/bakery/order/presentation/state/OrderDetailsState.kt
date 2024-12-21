package com.bakery.order.presentation.state

import com.bakery.core.types.order.Order

data class OrderDetailsState(
    val order: Order? = null,
)
