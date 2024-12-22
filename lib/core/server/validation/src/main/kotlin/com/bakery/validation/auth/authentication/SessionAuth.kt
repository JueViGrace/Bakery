package com.bakery.validation.auth.authentication

import com.bakery.core.database.helper.DbHelper
import com.bakery.core.types.APIResponse
import com.bakery.core.types.JwtAuthName
import com.bakery.core.types.ServerResponse
import com.bakery.core.util.Jwt
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

fun AuthenticationConfig.sessionAuth(
    name: JwtAuthName,
    jwt: Jwt,
    dbHelper: DbHelper
) {
    jwt(name = name.value) {
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
        challenge { _, _ ->
            call.request.headers["Authorization"]?.let { token ->
                dbHelper.withDatabase { db ->
                    db.transaction {
                        db.bakeryTokenQueries.deleteByToken(
                            token.split(" ")[1]
                        )
                    }
                }
            }
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ServerResponse.unauthorized(
                    message = "Token is invalid"
                ) as APIResponse.Failure
            )
        }
    }
}
