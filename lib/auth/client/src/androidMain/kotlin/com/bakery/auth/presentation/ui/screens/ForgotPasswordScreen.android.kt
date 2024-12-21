package com.bakery.auth.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.bakery.auth.presentation.viewmodel.ForgotPasswordViewModel
import com.bakery.core.presentation.ui.components.navigation.BackHandlerComponent

@Composable
actual fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,

) {
    BackHandlerComponent(viewModel.navigator)
}
