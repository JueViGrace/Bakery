package com.bakery.core.types.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.bakery.core.types.state.DataCodes
import com.bakery.core.types.state.RequestState
import kotlinx.coroutines.flow.Flow

@Composable
fun String.capitalizeString(): String {
    return this
        .lowercase()
        .split(" ")
        .joinToString(
            separator = " ",
            transform = { it.capitalize(Locale.current) }
        )
}

suspend inline fun <reified T> Flow<RequestState<T>>.unwrapResult(
    crossinline onSuccess: (T) -> Unit,
    crossinline onError: (DataCodes) -> Unit,
    crossinline onLoading: () -> Unit
) {
    collect { value ->
        when (value) {
            is RequestState.Error -> onError(value.error)
            is RequestState.Success -> onSuccess(value.data)
            else -> onLoading()
        }
    }
}
