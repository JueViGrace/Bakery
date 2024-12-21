package com.bakery.core.api.routes

import com.bakery.auth.routes.authRoutes
import com.bakery.order.routes.orderRoutes
import com.bakery.product.routes.productRoutes
import com.bakery.user.routes.userRoutes
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
    userRoutes()
    productRoutes()
    orderRoutes()
}

fun Route.webRoutes() {
    get {
        call.respond(
            status = HttpStatusCode.OK,
            message = "Root"
        )
    }
}
