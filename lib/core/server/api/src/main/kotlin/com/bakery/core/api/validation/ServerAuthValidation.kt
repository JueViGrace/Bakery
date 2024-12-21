package com.bakery.core.api.validation

import com.bakery.core.database.helper.DbHelper
import com.bakery.core.types.JwtAuthName
import com.bakery.core.types.OrderDataValidation
import com.bakery.core.types.Role
import com.bakery.core.types.UserIdValidation
import com.bakery.core.util.Jwt
import com.bakery.order.validation.ordersAuth
import com.bakery.user.validation.userAuth
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun AuthenticationConfig.serverAuthValidation(jwt: Jwt, dbHelper: DbHelper) {
    jwt(JwtAuthName.SESSION.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                extractId(credential)?.let { id ->
                    dbHelper.withDatabase { db ->
                        executeOne(db.bakeryTokenQueries.findTokenById(id))
                    }
                } ?: return@validateCredential null

                JWTPrincipal(credential.payload)
            }
        }
    }

    userAuth(
        name = JwtAuthName.USER.value,
        jwt = jwt,
        userCall = { id ->
            getUser(id, dbHelper)
        }
    )

    ordersAuth(
        name = JwtAuthName.ORDER.value,
        jwt = jwt,
        userCall = { id ->
            getUser(id, dbHelper)
        },
        orderCall = { id ->
            val order = dbHelper.withDatabase { db ->
                executeOne(db.bakeryOrderQueries.findOrder(id))
            }
            if (order == null) {
                return@ordersAuth null
            }

            OrderDataValidation(
                id = order.id,
                userId = order.user_id
            )
        }
    )

    jwt(JwtAuthName.ADMIN.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user = extractId(credential)?.let { id ->
                    getUser(id, dbHelper)
                }
                if (user == null) {
                    return@validateCredential null
                }

                if (user.isAdmin) {
                    return@validateCredential JWTPrincipal(credential.payload)
                }

                null
            }
        }
    }
}

private suspend fun getUser(id: String, dbHelper: DbHelper): UserIdValidation? {
    val dbUser = dbHelper.withDatabase { db ->
        executeOne(
            query = db.bakeryUserQueries.findUser(id)
        )
    }

    if (dbUser == null) {
        return null
    }

    return UserIdValidation(
        isAdmin = dbUser.role == Role.ADMIN.value,
        userId = dbUser.id
    )
}
