package com.bakery.server.config

import com.bakery.core.types.APIResponse
import com.bakery.core.types.ServerResponse.badRequest
import com.bakery.core.types.ServerResponse.forbidden
import com.bakery.core.types.ServerResponse.internalServerError
import com.bakery.core.types.ServerResponse.unauthorized
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("Routing")

    install(StatusPages) {
        exception<Throwable> { call: ApplicationCall, cause: Throwable ->
            logger.error("Unhandled error at: ${call.request.path()}", cause)
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = internalServerError(
                    message = "Something unexpected happened, try again later."
                ) as APIResponse.Failure
            )
        }

        exception<RequestValidationException> { call, cause ->
            logger.error("Validation error", cause)
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = badRequest(
                    data = cause.reasons.joinToString(", "),
                    message = "Invalid request."
                ) as APIResponse.Failure
            )
        }

        status(HttpStatusCode.Unauthorized) {
            val id = call.principal<JWTPrincipal>()?.payload?.getClaim("user_claims")?.asMap()["user_id"]
            if (id != null) {
                return@status call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = forbidden(
                        message = "You are not allowed to access this resource"
                    ) as APIResponse.Failure
                )
            }

            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = unauthorized(
                    message = "You are not authorized to access this endpoint"
                ) as APIResponse.Failure
            )
        }

        status(HttpStatusCode.Forbidden) {
            call.respond(
                status = HttpStatusCode.Forbidden,
                message = forbidden(
                    message = "You are not allowed to access this resource"
                ) as APIResponse.Failure
            )
        }
    }

//    install(SSE)

    routing {
        staticResources("/static", "static")
    }
}
