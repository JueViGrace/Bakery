package com.bakery.auth.data.storage

import com.bakery.core.database.Bakery_token
import com.bakery.core.database.Bakery_user
import com.bakery.core.database.helper.DbHelper
import com.bakery.core.shared.types.auth.AuthDto
import com.bakery.core.shared.types.auth.ForgotPasswordDto
import com.bakery.core.shared.types.auth.RefreshTokenDto
import com.bakery.core.shared.types.auth.SignInDto
import com.bakery.core.shared.types.auth.SignUpDto
import com.bakery.core.shared.types.user.UserDto
import com.bakery.core.types.auth.toDb
import com.bakery.core.util.Jwt
import com.bakery.core.util.Kbcrypt
import com.bakery.core.util.Util.verifyEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

interface AuthStore {
    suspend fun getValidUser(dto: SignInDto): AuthDto?
    suspend fun signUp(dto: SignUpDto): AuthDto?
    suspend fun refresh(dto: RefreshTokenDto): AuthDto?
    suspend fun forgotPassword(dto: ForgotPasswordDto): AuthDto?
}

class DefaultAuthStore(
    private val jwt: Jwt,
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : AuthStore {
    private suspend fun findUserByUsername(username: String): Bakery_user? {
        return dbHelper.withDatabase { db ->
            db.transactionWithResult {
                executeOne(
                    query = db.bakeryUserQueries.findUserByUsername(username)
                )
            }
        }
    }

    private suspend fun findUserByEmail(email: String): Bakery_user? {
        return dbHelper.withDatabase { db ->
            db.transactionWithResult {
                executeOne(
                    query = db.bakeryUserQueries.findUserByEmail(email)
                )
            }
        }
    }

    override suspend fun getValidUser(dto: SignInDto): AuthDto? {
        val dbUser: Bakery_user? = if (!verifyEmail(dto.username)) {
            findUserByUsername(dto.username)
        } else {
            findUserByEmail(dto.username)
        }

        if (dbUser == null || !Kbcrypt.verifyPassword(dto.password, dbUser.password)) {
            return null
        }

        return saveToken(
            userDto = UserDto(
                id = dbUser.id,
                firstName = dbUser.first_name,
                lastName = dbUser.last_name,
                username = dbUser.username,
                email = dbUser.email,
                phoneNumber = dbUser.phone_number,
                birthDate = dbUser.birth_date,
                address1 = dbUser.address1,
                address2 = dbUser.address2,
                gender = dbUser.gender,
                createdAt = dbUser.created_at,
            )
        )
    }

    override suspend fun signUp(dto: SignUpDto): AuthDto? {
        val dbUser = scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries.insert(
                        dto.copy(password = Kbcrypt.hashPassword(dto.password)).toDb()
                    ).executeAsOneOrNull()
                        ?: rollback(null)
                }
            }
        }.await()

        if (dbUser == null) {
            return null
        }

        return saveToken(
            userDto = UserDto(
                id = dbUser.id,
                firstName = dbUser.first_name,
                lastName = dbUser.last_name,
                username = dbUser.username,
                email = dbUser.email,
                phoneNumber = dbUser.phone_number,
                birthDate = dbUser.birth_date,
                address1 = dbUser.address1,
                address2 = dbUser.address2,
                gender = dbUser.gender,
                createdAt = dbUser.created_at,
            )
        )
    }

    // todo: delete old token?
    override suspend fun refresh(dto: RefreshTokenDto): AuthDto? {
        val token = jwt.verifyToken(dto.refreshToken) ?: return null

        val dbUser = dbHelper.withDatabase { db ->
            executeOne(
                query = db.bakeryUserQueries.findUser(token.userId)
            )
        }
        if (dbUser == null) {
            return null
        }

        return saveToken(
            userDto = UserDto(
                id = dbUser.id,
                firstName = dbUser.first_name,
                lastName = dbUser.last_name,
                username = dbUser.username,
                email = dbUser.email,
                phoneNumber = dbUser.phone_number,
                birthDate = dbUser.birth_date,
                address1 = dbUser.address1,
                address2 = dbUser.address2,
                gender = dbUser.gender,
                createdAt = dbUser.created_at,
            )
        )
    }

    override suspend fun forgotPassword(dto: ForgotPasswordDto): AuthDto? {
        return null
    }

    private suspend fun saveToken(
        userDto: UserDto,
    ): AuthDto? {
        val accessToken = jwt.createAccessToken(
            claims = mapOf(
                "user_id" to userDto.id,
            )
        )
        val refreshToken = jwt.createRefreshToken(
            claims = mapOf(
                "user_id" to userDto.id,
            )
        )

        scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryTokenQueries.insert(
                        bakery_token = Bakery_token(
                            token = refreshToken,
                            user_id = userDto.id
                        )
                    ).executeAsOneOrNull()
                }
            }
        }.await() ?: return null

        return AuthDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = userDto
        )
    }
}
