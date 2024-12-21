package com.bakery.core.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.bakery.core.types.Token
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlin.time.Duration

class Jwt(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
    val realm: String
) {
    companion object {
        private const val ACCESS_EXPIRES_IN: String = "3600000ms"

        private const val REFRESH_EXPIRES_IN: String = "86400000ms"
    }

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createAccessToken(claims: Map<String, String>): String =
        createJwt(claims, ACCESS_EXPIRES_IN)

    fun createRefreshToken(claims: Map<String, String>): String =
        createJwt(claims, REFRESH_EXPIRES_IN)

    private fun createJwt(claims: Map<String, String>, expiresIn: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("user_claims", claims)
            .withSubject(claims["user_id"])
            .withExpiresAt(Clock.System.now().plus(Duration.parse(expiresIn)).toJavaInstant())
            .sign(Algorithm.HMAC256(secret))
    }

    fun extractId(credential: JWTCredential): String? =
        credential.payload.getClaim("user_claims").asMap()["user_id"]?.toString()

    suspend fun validateCredential(credential: JWTCredential, block: suspend Jwt.() -> JWTPrincipal?): JWTPrincipal? {
        return when {
            !credential.payload.claims.containsKey("user_claims") -> null
            credential.payload.claims["user_id"]?.isNull == true -> null
            !credential.payload.audience.contains(audience) || credential.payload.audience.size > 1 -> null
            credential.payload.issuer != issuer -> null
            credential.payload.subject == null -> null
            credential.payload.expiresAtAsInstant < Clock.System.now().toJavaInstant() -> null
            else -> this.block()
        }
    }

    fun verifyToken(token: String): Token? {
        val decodedToken: DecodedJWT = jwtVerifier.verify(token) ?: return null

        if (decodedToken.expiresAtAsInstant < Clock.System.now().toJavaInstant()) {
            return null
        }

        val claims = decodedToken.getClaim("user_claims").asMap()

        if (
            claims == null ||
            claims["user_id"]?.toString().isNullOrEmpty()
        ) {
            return null
        }

        return Token(
            userId = claims["user_id"].toString(),
        )
    }
}
