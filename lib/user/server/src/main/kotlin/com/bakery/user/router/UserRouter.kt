package com.bakery.user.router

import com.bakery.core.types.JwtAuthName
import com.bakery.user.data.handler.UserHandler
import com.bakery.user.router.routes.admin.adminGetUserById
import com.bakery.user.router.routes.admin.deleteUser
import com.bakery.user.router.routes.admin.getUsers
import com.bakery.user.router.routes.getUserById
import com.bakery.user.router.routes.softDeleteUser
import com.bakery.user.router.routes.updateUser
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.userRouter() {
    val handler: UserHandler by inject<UserHandler>()

    route("/users") {
        // Admin user auth
        authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
            route("/admin") {
                getUsers(handler)

                adminGetUserById(handler)

                deleteUser(handler)
            }
        }

        getUserById(handler)

        updateUser(handler)

        softDeleteUser(handler)
    }
}
