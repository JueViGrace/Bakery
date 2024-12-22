package com.bakery.core.api.routes

import com.bakery.auth.router.authRoutes
import com.bakery.order.router.orderRouter
import com.bakery.product.router.productRouter
import com.bakery.user.router.userRouter
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Routing.serverRoutes() {
    route("/api") {
        apiRoutes()
    }
    webRoutes()
}

fun Route.apiRoutes() {
    get("/health") {
        call.respond(
            status = HttpStatusCode.OK,
            message = "Health is ok!"
        )
    }

    authRoutes()
    userRouter()
    productRouter()
    orderRouter()
}

fun Route.webRoutes() {
    get {
        call.respond(
            status = HttpStatusCode.OK,
            message = "Root"
        )
    }
}
