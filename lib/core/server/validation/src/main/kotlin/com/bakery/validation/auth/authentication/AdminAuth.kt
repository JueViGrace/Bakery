package com.bakery.validation.auth.authentication

import com.bakery.core.database.helper.DbHelper
import com.bakery.core.types.APIResponse
import com.bakery.core.types.JwtAuthName
import com.bakery.core.types.Role
import com.bakery.core.types.ServerResponse
import com.bakery.core.util.Jwt
import com.bakery.validation.AuthHelper.getUserId
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

fun AuthenticationConfig.adminAuth(
    name: JwtAuthName,
    jwt: Jwt,
    dbHelper: DbHelper
) {
    jwt(name = name.value) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val user = getUserId(credential, dbHelper)
                    ?: return@validateCredential null

                if (user.role == Role.ADMIN) {
                    JWTPrincipal(credential.payload)
                }
                null
            }
        }
        challenge { _, _ ->
            call.respond(
                status = HttpStatusCode.Forbidden,
                message = ServerResponse.forbidden(
                    message = "Forbidden resource"
                ) as APIResponse.Failure
            )
        }
    }
}