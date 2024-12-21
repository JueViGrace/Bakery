package com.bakery.auth.data.handler

import com.bakery.auth.data.storage.AuthStore
import com.bakery.core.shared.types.auth.AuthDto
import com.bakery.core.shared.types.auth.ForgotPasswordDto
import com.bakery.core.shared.types.auth.RefreshTokenDto
import com.bakery.core.shared.types.auth.SignInDto
import com.bakery.core.shared.types.auth.SignUpDto
import com.bakery.core.types.APIResponse
import com.bakery.core.types.ServerResponse
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

//todo: check for conflicts
interface AuthHandler {
    suspend fun signIn(dto: SignInDto): APIResponse<AuthDto?>
    suspend fun signUp(dto: SignUpDto): APIResponse<AuthDto?>
    suspend fun refresh(dto: RefreshTokenDto): APIResponse<AuthDto?>
    suspend fun forgotPassword(dto: ForgotPasswordDto): APIResponse<AuthDto?>
}

class DefaultAuthHandler(
    private val coroutineContext: CoroutineContext,
    private val store: AuthStore
) : AuthHandler {
    override suspend fun signIn(dto: SignInDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.getValidUser(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Invalid credentials"
                )

            return@withContext ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun signUp(dto: SignUpDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.signUp(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Unable to create user, try again later."
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun refresh(dto: RefreshTokenDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.refresh(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Invalid refresh token"
                )

            return@withContext ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    // todo: do this right
    override suspend fun forgotPassword(dto: ForgotPasswordDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.forgotPassword(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Unable to process change of password, try again."
                )

            return@withContext ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
