package com.bakery.app.presentation.state

import com.bakery.core.types.auth.Session
import org.jetbrains.compose.resources.StringResource

data class AppState(
    val session: Session? = null,
    val snackMessage: StringResource? = null,
    val description: String = "",
)
