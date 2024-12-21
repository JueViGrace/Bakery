package com.bakery.core.types

import io.ktor.server.application.ApplicationCall

inline fun<reified T> ApplicationCall.applicationResponse(
    response: APIResponse<T>,
    onSuccess: (APIResponse.Success<T>) -> Unit,
    onFailure: (APIResponse.Failure) -> Unit
) {
    when (response) {
        is APIResponse.Failure -> onFailure(response)
        is APIResponse.Success -> onSuccess(response)
    }
}
