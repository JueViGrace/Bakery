package com.bakery.core.types.response

import com.bakery.core.types.state.AppCodes
import com.bakery.core.types.state.DataCodes

sealed interface ApiOperation<out T> {
    data class Success<T>(val value: APIResponse<T>) : ApiOperation<T>
    data class Failure(val error: AppCodes) : ApiOperation<Nothing>
}

inline fun <reified T, reified R> ApiOperation<T>.display(
    onSuccess: (APIResponse<T>) -> R,
    onFailure: (DataCodes) -> R
): R {
    return when (this) {
        is ApiOperation.Success -> onSuccess(value)
        is ApiOperation.Failure -> onFailure(DataCodes.fromCode(error))
    }
}
