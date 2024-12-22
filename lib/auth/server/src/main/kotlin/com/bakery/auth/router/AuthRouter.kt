package com.bakery.auth.router

import com.bakery.auth.data.handler.AuthHandler
import com.bakery.auth.router.routes.forgotPassword
import com.bakery.auth.router.routes.refresh
import com.bakery.auth.router.routes.signIn
import com.bakery.auth.router.routes.signUp
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val handler: AuthHandler by inject<AuthHandler>()

    route("/auth") {
        signIn(handler)

        signUp(handler)

        // this should send an email or receive the new password
        forgotPassword(handler)

        // todo: maybe session auth
        refresh(handler)
    }
}
