package com.bakery.order.router.routes.admin

import com.bakery.core.shared.types.order.UpdateOrderDto
import com.bakery.core.types.applicationResponse
import com.bakery.order.data.handler.OrderHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch

fun Route.updateOrderStatus(handler: OrderHandler) {
    patch<UpdateOrderDto> { dto ->
        val response = handler.updateOrderStatus(dto)

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
