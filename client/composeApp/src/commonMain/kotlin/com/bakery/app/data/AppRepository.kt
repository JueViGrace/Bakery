package com.bakery.app.data

import com.bakery.core.api.KtorClient
import com.bakery.core.database.helper.DbHelper
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.session_expired
import com.bakery.core.resources.resources.generated.resources.unexpected_error
import com.bakery.core.resources.resources.generated.resources.unknown_error
import com.bakery.core.shared.types.auth.AuthDto
import com.bakery.core.shared.types.auth.RefreshTokenDto
import com.bakery.core.types.Repository
import com.bakery.core.types.auth.Session
import com.bakery.core.types.auth.dtoToDomain
import com.bakery.core.types.auth.findToDomain
import com.bakery.core.types.auth.sessionToDb
import com.bakery.core.types.response.ApiOperation
import com.bakery.core.types.response.display
import com.bakery.core.types.state.DataCodes
import com.bakery.core.types.state.RequestState
import com.bakery.core.types.user.domainToDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

interface AppRepository : Repository {
    fun validateSession(): Flow<RequestState<Session>>
}

class DefaultAppRepository(
    private val ktorClient: KtorClient,
    private val dbHelper: DbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AppRepository {
    override fun validateSession(): Flow<RequestState<Session>> {
        return flow {
            emit(RequestState.Loading)

            val session = dbHelper.withDatabase { db ->
                executeOne(
                    query = db.bakerySessionQueries.findActiveAccount()
                )
            }

            if (session != null) {
                val refreshCall = refresh(session.refresh_token).display(
                    onFailure = { code ->
                        endSession()
                        RequestState.Error(
                            error = code
                        )
                    },
                    onSuccess = { res ->
                        if (res.data == null) {
                            endSession()
                            return@display RequestState.Error(
                                error = DataCodes.NullError(
                                    msg = Res.string.session_expired,
                                    desc = res.message
                                )
                            )
                        }
                        handleSuccessRefresh(
                            session = res.data!!.dtoToDomain(),
                        )
                    }
                )
                emit(refreshCall)
            }
            emit(
                RequestState.Error(
                    error = DataCodes.NullError(
                        desc = "Session is null"
                    )
                )
            )
        }.flowOn(coroutineContext)
    }

    private suspend fun refresh(refreshToken: String): ApiOperation<AuthDto> {
        return ktorClient.call {
            post(
                urlString = "/api/auth/refresh",
                body = RefreshTokenDto(
                    refreshToken = refreshToken
                )
            )
        }
    }

    private suspend fun handleSuccessRefresh(
        session: Session,
    ): RequestState<Session> {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries.insert(
                        bakery_user = session.user.domainToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    db.bakerySessionQueries.insert(
                        bakery_session = session.copy(active = true).sessionToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                }
            }
        }.await()
            ?: return RequestState.Error(
                error = DataCodes.NullError(
                    msg = Res.string.unexpected_error,
                    desc = "Unable to update session"
                )
            )

        val newSession = dbHelper.withDatabase { db ->
            executeOne(
                query = db.bakerySessionQueries.findActiveAccount()
            )
        }
            ?: return RequestState.Error(
                error = DataCodes.NullError(
                    msg = Res.string.unknown_error,
                    desc = "Unable to find session"
                )
            )

        return RequestState.Success(newSession.findToDomain())
    }

    private suspend fun endSession() {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.bakerySessionQueries.endSession()
            }
        }
    }
}
