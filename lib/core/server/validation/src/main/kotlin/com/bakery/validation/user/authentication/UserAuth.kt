package com.bakery.validation.user.authentication

import com.bakery.core.shared.types.user.UpdateUserDto
import com.bakery.core.types.UserIdValidation
import com.bakery.core.util.Jwt
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json

fun AuthenticationConfig.userAuth(
    name: String,
    jwt: Jwt,
    userCall: suspend (String) -> UserIdValidation?
) {
    jwt(name = name) {
        realm = jwt.realm

        verifier(jwt.jwtVerifier)

        validate { credential ->
            jwt.validateCredential(credential) {
                val tokenId = extractId(credential) ?: return@validateCredential null

                val user = userCall(tokenId) ?: return@validateCredential null


                JWTPrincipal(credential.payload)
            }
        }
    }
}
