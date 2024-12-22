package com.bakery.user.router.routes.admin

import com.bakery.core.types.ServerResponse
import com.bakery.core.types.applicationResponse
import com.bakery.user.data.handler.UserHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

fun Route.deleteUser(handler: UserHandler) {
    delete("/forever/{id}") {
        val id = call.parameters["id"]
            ?: return@delete call.respond(
                status = HttpStatusCode.BadRequest,
                message = ServerResponse.badRequest<String?>(
                    message = "Missing id"
                )
            )
        val response = handler.deleteUser(id)

        call.applicationResponse(
            response = response,
            onFailure = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description,
                    ),
                    message = res
                )
            },
            onSuccess = { res ->
                call.respond(
                    status = HttpStatusCode(
                        value = res.status,
                        description = res.description,
                    ),
                    message = res
                )
            }
        )
    }
}
