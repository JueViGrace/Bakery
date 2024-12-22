package com.bakery.auth.router.routes

import com.bakery.auth.data.handler.AuthHandler
import com.bakery.core.shared.types.auth.RefreshTokenDto
import com.bakery.core.types.applicationResponse
import io.ktor.http.Cookie
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.refresh(handler: AuthHandler) {
    post("/refresh") {
        val body = call.receive<RefreshTokenDto>()
        val response = handler.refresh(body)

        call.applicationResponse(
            response = response,
            onSuccess = { res ->
                res.data?.refreshToken?.let { value -> call.response.cookies.append(Cookie("refreshToken", value)) }

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
