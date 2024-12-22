package com.bakery.order.router

import com.bakery.core.types.JwtAuthName
import com.bakery.order.data.handler.OrderHandler
import com.bakery.order.router.routes.admin.getOrders
import com.bakery.order.router.routes.admin.updateOrderStatus
import com.bakery.order.router.routes.cancelOrder
import com.bakery.order.router.routes.createOrder
import com.bakery.order.router.routes.getOrderById
import com.bakery.order.router.routes.getOrdersByUser
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.orderRouter() {
    val handler: OrderHandler by inject<OrderHandler>()

    route("/orders") {
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                getOrders(handler)

                updateOrderStatus(handler)
            }
        }
        getOrdersByUser(handler)

        getOrderById(handler)

        createOrder(handler)

        cancelOrder(handler)
    }
}
