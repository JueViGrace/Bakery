package com.bakery.auth.router.routes

import com.bakery.auth.data.handler.AuthHandler
import com.bakery.core.shared.types.auth.ForgotPasswordDto
import com.bakery.core.types.applicationResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.forgotPassword(handler: AuthHandler) {
    post("/forgotPassword") {
        val body = call.receive<ForgotPasswordDto>()
        val response = handler.forgotPassword(body)

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
