package com.bakery.user.router.routes

import com.bakery.core.shared.types.user.UpdateUserDto
import com.bakery.core.types.applicationResponse
import com.bakery.user.data.handler.UserHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch

fun Route.updateUser(handler: UserHandler) {
// todo: routes for changing email and username
    patch<UpdateUserDto> { body ->
        val response = handler.updateUser(body)

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
