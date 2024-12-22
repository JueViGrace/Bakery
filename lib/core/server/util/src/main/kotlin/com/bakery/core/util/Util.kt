package com.bakery.core.util

import com.bakery.core.types.Role
import java.util.regex.Pattern
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object Util {
    fun verifyEmail(string: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )

        return pattern.matcher(string).matches()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun validUuid(id: String): Boolean {
        return try {
            Uuid.parse(id)
            true
        } catch (e: IllegalStateException) {
            false
        }
    }

    fun verifyUserRole(role: String): Role {
        return try {
            when (val value = Role.valueOf(role)) {
                Role.CUSTOMER -> value
                Role.USER -> value
                Role.ADMIN -> value
                else -> Role.UNIDENTIFIED
            }
        } catch (e: IllegalArgumentException) {
            println("Invalid role: $role")
            println(e.message)
            Role.UNIDENTIFIED
        }
    }
}
