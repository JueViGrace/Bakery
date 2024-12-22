package com.bakery.validation.order.authentication

import com.bakery.core.types.OrderDataValidation
import com.bakery.core.types.UserIdValidation
import com.bakery.core.util.Jwt
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun AuthenticationConfig.ordersAuth(
    name: String,
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?,
    orderCall: suspend (String) -> OrderDataValidation?
) {
    jwt(name = name) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val tokenId = extractId(credential) ?: return@validateCredential null

                val user = userCall(tokenId) ?: return@validateCredential null

                if (user.isAdmin) {
                    return@validateCredential JWTPrincipal(credential.payload)
                }

                JWTPrincipal(credential.payload)
            }
        }
    }
}
