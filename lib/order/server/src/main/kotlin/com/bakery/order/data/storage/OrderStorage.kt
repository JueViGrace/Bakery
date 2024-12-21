package com.bakery.order.data.storage

import app.cash.sqldelight.SuspendingTransactionWithReturn
import com.bakery.core.database.BakerySvDb
import com.bakery.core.database.helper.DbHelper
import com.bakery.core.shared.types.order.CreateOrderDetailsDto
import com.bakery.core.shared.types.order.CreateOrderDto
import com.bakery.core.shared.types.order.OrderDto
import com.bakery.core.shared.types.order.UpdateOrderDto
import com.bakery.core.shared.types.order.OrderStatus
import com.bakery.core.types.order.orderByUserToDto
import com.bakery.core.types.order.orderLinesToDto
import com.bakery.core.types.order.toDb
import com.bakery.core.types.order.toDbDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

interface OrderStorage {
    suspend fun getOrders(): List<OrderDto>
    suspend fun getOrderWithLines(id: String): OrderDto?
    suspend fun getOrdersByUser(userId: String): List<OrderDto>
    suspend fun getOrderByUserWithLines(orderId: String, userId: String): OrderDto?
    suspend fun createOrder(dto: CreateOrderDto): OrderDto?
    suspend fun updateOrderStatus(dto: UpdateOrderDto): OrderDto?
}

class DefaultOrderStorage(
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : OrderStorage {
    override suspend fun getOrders(): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.bakeryOrderQueries.findOrders()
            ).map { order ->
                order.orderLinesToDto()
            }
        }
    }

    override suspend fun getOrderWithLines(id: String): OrderDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.bakeryOrderQueries.findOrderWithLines(id)
            ).orderLinesToDto()
        }
    }

    override suspend fun getOrdersByUser(userId: String): List<OrderDto> {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.bakeryOrderQueries.findOrdersByUser(userId)
            ).map { order ->
                order.orderLinesToDto()
            }
        }
    }

    override suspend fun getOrderByUserWithLines(orderId: String, userId: String): OrderDto? {
        return dbHelper.withDatabase { db ->
            dbHelper.executeList(
                query = db.bakeryOrderQueries.findOrderByUserWithLines(
                    id = orderId,
                    user_id = userId
                )
            ).orderByUserToDto()
        }
    }

    override suspend fun createOrder(dto: CreateOrderDto): OrderDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val order = insertOrder(db, dto)
                    insertOrderLines(db, dto.details, order.id)
                    updateProductStock(db, dto.details)
                    order
                }
            }
        }.await()
    }

    override suspend fun updateOrderStatus(dto: UpdateOrderDto): OrderDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val order = executeList(
                        query = db.bakeryOrderQueries.findOrderWithLines(dto.id)
                    ).orderLinesToDto()
                    if (order == null) {
                        rollback(null)
                    }

                    // todo: product stock updates could be broke down into one or separate functions
                    val updatedOrder: OrderDto? = when (OrderStatus.valueOf(dto.status)) {
                        OrderStatus.Placed -> null
                        OrderStatus.Cancelled -> onOrderCancel(db, order)
                        OrderStatus.Dispatched -> onOrderDispatched(db, order)
                        OrderStatus.Delivered -> onOrderDelivered(db, order.id)
                    }

                    updatedOrder
                }
            }
        }.await()
    }

    private fun SuspendingTransactionWithReturn<OrderDto?>.insertOrder(
        db: BakerySvDb,
        dto: CreateOrderDto
    ): OrderDto {
        val totalAmount = dto.details.sumOf { detail ->
            calculateOrderTotalAmount(
                quantity = detail.quantity,
                price = detail.price,
                discount = detail.discount
            )
        }

        val order = db.bakeryOrderQueries
            .insert(
                dto.toDb(
                    totalAmount = totalAmount
                )
            )
            .executeAsOneOrNull()
            ?.orderLinesToDto()
        if (order == null) {
            rollback(null)
        }

        return order
    }

    private fun SuspendingTransactionWithReturn<OrderDto?>.insertOrderLines(
        db: BakerySvDb,
        details: List<CreateOrderDetailsDto>,
        orderId: String
    ) {
        val list = details.map { detail ->
            db.bakeryOrderProductsQueries.insert(
                detail.toDbDetails(
                    orderId = orderId,
                    totalPrice = calculateOrderTotalAmount(
                        quantity = detail.quantity,
                        price = detail.price,
                        discount = detail.discount
                    )
                )
            )
                .executeAsOneOrNull()
        }

        if (list.isEmpty() || list.contains(null)) {
            rollback(null)
        }
    }

    private fun SuspendingTransactionWithReturn<OrderDto?>.updateProductStock(
        db: BakerySvDb,
        details: List<CreateOrderDetailsDto>
    ) {
        val list = details.map { detail ->
            val product = db.bakeryProductQueries
                .findProduct(detail.productId)
                .executeAsOneOrNull()

            if (product == null) {
                return@map null
            }

            val stock = product.stock - detail.quantity
            if (stock < 0) {
                return@map null
            }

            val updatedProduct = db.bakeryProductQueries.updateStock(
                stock = stock,
                issued = product.issued + detail.quantity,
                has_stock = if (stock > 0) 1 else 0,
                id = product.id
            )
                .executeAsOneOrNull()

            if (updatedProduct == null) {
                return@map null
            }

            return@map updatedProduct
        }

        if (list.isEmpty() || list.contains(null)) {
            rollback(null)
        }
    }

    private fun calculateOrderTotalAmount(
        quantity: Int,
        price: Double,
        discount: Double
    ): Double {
        return quantity * (price - (price * discount / 100))
    }

    // todo: add date constraint to cancellation
    private fun SuspendingTransactionWithReturn<OrderDto?>.onOrderCancel(
        db: BakerySvDb,
        order: OrderDto
    ): OrderDto {
        val updatedOrder = db.bakeryOrderQueries.updateStatus(
            status = OrderStatus.Cancelled.value,
            id = order.id
        )
            .executeAsOneOrNull()
        if (updatedOrder == null) {
            rollback(null)
        }

        val list = order.details.map { detail ->
            val product = db.bakeryProductQueries
                .findProduct(detail.productId)
                .executeAsOneOrNull()
            if (product == null) {
                return@map null
            }

            val stock = product.stock + detail.quantity

            val updatedProduct = db.bakeryProductQueries.updateStock(
                stock = stock,
                issued = product.issued - detail.quantity,
                has_stock = if (stock > 0) 1 else 0,
                id = product.id
            )
                .executeAsOneOrNull()
            if (updatedProduct == null) {
                return@map null
            }

            return@map updatedProduct
        }
        if (list.isEmpty() || list.contains(null)) {
            rollback(null)
        }

        return updatedOrder.orderLinesToDto()
    }

    private fun SuspendingTransactionWithReturn<OrderDto?>.onOrderDispatched(
        db: BakerySvDb,
        order: OrderDto
    ): OrderDto {
        val updatedOrder = db.bakeryOrderQueries.updateStatus(
            status = OrderStatus.Dispatched.value,
            id = order.id
        )
            .executeAsOneOrNull()
        if (updatedOrder == null) {
            rollback(null)
        }

        val list = order.details.map { detail ->
            val product = db.bakeryProductQueries
                .findProduct(detail.productId)
                .executeAsOneOrNull()
            if (product == null) {
                return@map null
            }

            val updatedProduct = db.bakeryProductQueries.updateIssued(
                issued = product.issued - detail.quantity,
                id = product.id
            )
                .executeAsOneOrNull()
            if (updatedProduct == null) {
                return@map null
            }

            return@map updatedProduct
        }
        if (list.isEmpty() || list.contains(null)) {
            rollback(null)
        }

        return updatedOrder.orderLinesToDto()
    }

    private fun SuspendingTransactionWithReturn<OrderDto?>.onOrderDelivered(
        db: BakerySvDb,
        orderId: String
    ): OrderDto {
        val updatedOrder = db.bakeryOrderQueries.updateStatus(
            status = OrderStatus.Delivered.value,
            id = orderId
        )
            .executeAsOneOrNull()
        if (updatedOrder == null) {
            rollback(null)
        }
        return updatedOrder.orderLinesToDto()
    }
}
