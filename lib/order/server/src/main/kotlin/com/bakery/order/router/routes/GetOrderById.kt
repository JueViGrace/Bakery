package com.bakery.order.router.routes

import com.bakery.core.types.ServerResponse
import com.bakery.core.types.applicationResponse
import com.bakery.order.data.handler.OrderHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getOrderById(handler: OrderHandler) {
    get("/{id}") {
        val id = call.parameters["id"]
            ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Missing id"
                )
            )
        val response = handler.getOrderWithLines(id)

        call.applicationResponse(
            response = response,
            onSuccess = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description
                    ),
                    message = res
                )
            },
            onFailure = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description
                    ),
                    message = res
                )
            }
        )
    }
}
