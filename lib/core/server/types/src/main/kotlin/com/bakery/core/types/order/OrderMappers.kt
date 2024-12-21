package com.bakery.core.types.order

import com.bakery.core.shared.types.Constants
import com.bakery.core.shared.types.order.CancelOrderDto
import com.bakery.core.shared.types.order.CreateOrderDetailsDto
import com.bakery.core.shared.types.order.CreateOrderDto
import com.bakery.core.shared.types.order.OrderDetailsDto
import com.bakery.core.shared.types.order.OrderDto
import com.bakery.core.shared.types.order.UpdateOrderDto
import com.bakery.core.shared.types.order.OrderStatus
import com.bakery.core.types.aliases.DbOrder
import com.bakery.core.types.aliases.OrderByUserWithLines
import com.bakery.core.types.aliases.OrderLines
import com.bakery.core.types.aliases.OrderWithLines
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun DbOrder.orderLinesToDto(): OrderDto = OrderDto(
    id = id,
    totalAmount = total_amount,
    paymentMethod = payment_method,
    status = status,
    userId = user_id,
    createdAt = created_at,
    details = emptyList(),
)

// todo: this should be done in other way

fun List<OrderWithLines>.orderLinesToDto(): OrderDto? {
    val group: Map<OrderDto, List<OrderWithLines>> = this.groupBy { row ->
        OrderDto(
            id = row.id,
            totalAmount = row.total_amount,
            paymentMethod = row.payment_method,
            status = row.status,
            userId = row.user_id,
            createdAt = row.created_at,
            details = emptyList(),
        )
    }

    val dto: OrderDto? = group.map { (order: OrderDto, rows: List<OrderWithLines>) ->
        return@map OrderDto(
            id = order.id,
            totalAmount = order.totalAmount,
            paymentMethod = order.paymentMethod,
            status = order.status,
            userId = order.userId,
            createdAt = order.createdAt,
            details = rows.orderLinesToDetails()
        )
    }.firstOrNull()

    return dto
}

fun List<OrderWithLines>.orderLinesToDetails(): List<OrderDetailsDto> {
    this.firstOrNull { row -> row.order_id == null } ?: return emptyList()

    val details: List<OrderDetailsDto> = this.map { row ->
        return@map OrderDetailsDto(
            orderId = row.order_id ?: "",
            productId = row.product_id ?: "",
            totalPrice = row.total_price ?: 0.0,
            quantity = row.quantity?.toInt() ?: 0,
            name = row.product_name ?: "",
            category = row.category ?: "",
            price = row.price ?: 0.0,
            discount = row.discount ?: 0.0,
            rating = row.rating ?: 0.0,
            images = row.images?.split(",") ?: emptyList(),
        )
    }

    return details
}

fun List<OrderByUserWithLines>.orderByUserToDto(): OrderDto? {
    val group: Map<OrderDto, List<OrderByUserWithLines>> = this.groupBy { row ->
        OrderDto(
            id = row.id,
            totalAmount = row.total_amount,
            paymentMethod = row.payment_method,
            status = row.status,
            userId = row.user_id,
            createdAt = row.created_at,
            details = emptyList(),
        )
    }

    val dto: OrderDto? = group.map { (order: OrderDto, rows: List<OrderByUserWithLines>) ->
        return@map OrderDto(
            id = order.id,
            totalAmount = order.totalAmount,
            paymentMethod = order.paymentMethod,
            status = order.status,
            userId = order.userId,
            createdAt = order.createdAt,
            details = rows.orderByUserToDetails()
        )
    }.firstOrNull()

    return dto
}

fun List<OrderByUserWithLines>.orderByUserToDetails(): List<OrderDetailsDto> {
    this.firstOrNull { row -> row.order_id == null } ?: return emptyList()

    val details: List<OrderDetailsDto> = this.map { row ->
        return@map OrderDetailsDto(
            orderId = row.order_id ?: "",
            productId = row.product_id ?: "",
            totalPrice = row.total_price ?: 0.0,
            quantity = row.quantity?.toInt() ?: 0,
            name = row.product_name ?: "",
            category = row.category ?: "",
            price = row.price ?: 0.0,
            discount = row.discount ?: 0.0,
            rating = row.rating ?: 0.0,
            images = row.images?.split(",") ?: emptyList(),
        )
    }

    return details
}

@OptIn(ExperimentalUuidApi::class)
fun CreateOrderDto.toDb(
    totalAmount: Double
): DbOrder = DbOrder(
    id = Uuid.random().toString(),
    total_amount = totalAmount,
    payment_method = paymentMethod,
    status = OrderStatus.Placed.value,
    user_id = userId,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
)

fun CreateOrderDetailsDto.toDbDetails(orderId: String, totalPrice: Double): OrderLines = OrderLines(
    order_id = orderId,
    product_id = productId,
    quantity = quantity.toLong(),
    product_name = name,
    total_price = totalPrice,
    price = price,
    discount = discount,
    rating = rating,
)

fun CancelOrderDto.toUpdateDto(): UpdateOrderDto = UpdateOrderDto(
    id = id,
    status = OrderStatus.Cancelled.value,
)
