package com.bakery.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.bakery.auth.data.repository.AuthRepository
import com.bakery.auth.presentation.state.ForgotPasswordState
import com.bakery.core.presentation.messages.Messages
import com.bakery.core.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPasswordViewModel(
    val navigator: Navigator,
    private val messages: Messages,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    private val state = _state.asStateFlow()
}
