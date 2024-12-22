package com.bakery.validation

import com.bakery.core.database.helper.DbHelper
import com.bakery.core.types.UserIdValidation
import com.bakery.core.util.Jwt
import com.bakery.core.util.Util.verifyUserRole
import io.ktor.server.auth.jwt.JWTCredential

object AuthHelper {
    private suspend fun getUserData(id: String, dbHelper: DbHelper): UserIdValidation? {
        val user = dbHelper.withDatabase { db ->
            executeOne(db.bakeryUserQueries.findExistingUser(id))
        } ?: return null

        return UserIdValidation(
            role = verifyUserRole(user.role),
            userId = user.id,
            username = user.username,
        )
    }

    suspend fun Jwt.getUserId(credential: JWTCredential, dbHelper: DbHelper): UserIdValidation? {
        return extractId(credential)?.let { id ->
            getUserData(id, dbHelper)
        }
    }
}
