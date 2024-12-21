package com.bakery.order.data.handler

import com.bakery.core.types.APIResponse
import com.bakery.core.types.ServerResponse
import com.bakery.order.data.storage.OrderStorage
import com.bakery.core.shared.types.order.CreateOrderDto
import com.bakery.core.shared.types.order.OrderDto
import com.bakery.core.shared.types.order.UpdateOrderDto
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface OrderHandler {
    suspend fun getOrders(): APIResponse<List<OrderDto>>
    suspend fun getOrderWithLines(id: String): APIResponse<OrderDto?>
    suspend fun getOrdersByUser(userId: String): APIResponse<List<OrderDto>>
    suspend fun getOrderByUserWithLines(orderId: String, userId: String): APIResponse<OrderDto?>
    suspend fun createOrder(dto: CreateOrderDto): APIResponse<OrderDto?>
    suspend fun updateOrderStatus(dto: UpdateOrderDto): APIResponse<OrderDto?>
}

class DefaultOrderHandler(
    private val coroutineContext: CoroutineContext,
    private val storage: OrderStorage
) : OrderHandler {
    override suspend fun getOrders(): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getOrders()

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "No orders were found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrderWithLines(id: String): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.getOrderWithLines(id)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Order with id $id was not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrdersByUser(userId: String): APIResponse<List<OrderDto>> {
        return withContext(coroutineContext) {
            val result = storage.getOrdersByUser(userId)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "No orders were found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getOrderByUserWithLines(
        orderId: String,
        userId: String,
    ): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.getOrderByUserWithLines(orderId, userId)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Order with id $orderId was not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createOrder(dto: CreateOrderDto): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.createOrder(dto)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Unable to create order, try again later"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun updateOrderStatus(dto: UpdateOrderDto): APIResponse<OrderDto?> {
        return withContext(coroutineContext) {
            val result = storage.updateOrderStatus(dto)

            if (result == null) {
                return@withContext ServerResponse.notFound(
                    message = "Unable to update order status, try again later"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
