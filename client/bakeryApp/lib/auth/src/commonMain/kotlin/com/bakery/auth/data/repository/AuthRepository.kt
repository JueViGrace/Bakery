package com.bakery.auth.data.repository

import com.bakery.core.api.KtorClient
import com.bakery.core.database.helper.DbHelper
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.unknown_error
import com.bakery.core.resources.resources.generated.resources.welcome
import com.bakery.core.resources.resources.generated.resources.welcome_back
import com.bakery.core.shared.types.auth.AuthDto
import com.bakery.core.shared.types.auth.ForgotPasswordDto
import com.bakery.core.shared.types.auth.SignInDto
import com.bakery.core.shared.types.auth.SignUpDto
import com.bakery.core.types.Repository
import com.bakery.core.types.auth.Session
import com.bakery.core.types.auth.dtoToDomain
import com.bakery.core.types.auth.sessionToDb
import com.bakery.core.types.response.display
import com.bakery.core.types.state.DataCodes
import com.bakery.core.types.state.RequestState
import com.bakery.core.types.user.domainToDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

interface AuthRepository : Repository {
    suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes>
    suspend fun signUp(signUpDto: SignUpDto): RequestState<DataCodes>
    suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes>
}

class DefaultAuthRepository(
    private val ktorClient: KtorClient,
    private val dbHelper: DbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AuthRepository {
    override suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/signIn",
                body = signInDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(error = code)
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }

                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.welcome,
                        desc = res.message
                    )
                )
            }
        )
    }

    override suspend fun signUp(signUpDto: SignUpDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/signUp",
                body = signUpDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(
                    error = code
                )
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }
                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.welcome_back,
                        desc = res.message
                    )
                )
            }
        )
    }

    override suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/forgotPassword",
                body = forgotPasswordDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(
                    error = code
                )
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }
                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.welcome_back,
                        desc = res.message
                    )
                )
            }
        )
    }

    private suspend fun saveSession(session: Session) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.bakerySessionQueries.insert(
                        bakery_session = session.sessionToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback()

                    db.bakeryUserQueries.insert(
                        bakery_user = session.user.domainToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback()
                }
            }
        }.await()
    }
}
