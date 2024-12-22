package com.bakery.validation

import com.bakery.core.database.helper.DbHelper
import com.bakery.core.types.JwtAuthName
import com.bakery.core.util.Jwt
import com.bakery.validation.auth.authentication.adminAuth
import com.bakery.validation.auth.authentication.sessionAuth
import io.ktor.server.auth.AuthenticationConfig

fun AuthenticationConfig.serverAuthValidation(jwt: Jwt, dbHelper: DbHelper) {
    sessionAuth(
        name = JwtAuthName.SESSION,
        jwt = jwt,
        dbHelper = dbHelper
    )

    adminAuth(
        name = JwtAuthName.ADMIN,
        jwt = jwt,
        dbHelper = dbHelper
    )
}
