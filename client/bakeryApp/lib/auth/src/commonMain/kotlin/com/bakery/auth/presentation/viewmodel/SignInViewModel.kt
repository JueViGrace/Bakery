package com.bakery.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.bakery.auth.data.repository.AuthRepository
import com.bakery.auth.domain.model.SignIn
import com.bakery.auth.domain.rules.AuthValidator
import com.bakery.auth.presentation.events.SignInEvents
import com.bakery.auth.presentation.state.SignInState
import com.bakery.core.presentation.messages.Messages
import com.bakery.core.presentation.navigation.Destination
import com.bakery.core.presentation.navigation.Navigator
import com.bakery.core.shared.types.auth.SignInDto
import com.bakery.core.types.state.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val navigator: Navigator,
    private val authRepository: AuthRepository,
    private val messages: Messages
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInEvents) {
        when (event) {
            SignInEvents.OnNavigateToForgotPassword -> navigateForgotPassword()
            SignInEvents.OnNavigateToSignUp -> navigateToSignUp()
            is SignInEvents.OnSignInUsernameChanged -> signInUsernameChanged(event.value)
            is SignInEvents.OnSignInPasswordChanged -> signInPasswordChanged(event.value)
            SignInEvents.OnSignInSubmit -> signInSubmit()
            SignInEvents.TogglePasswordVisibility -> togglePasswordVisibility()
        }
    }

    private fun navigateForgotPassword() {
        resetState()
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.ForgotPassword,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(route = Destination.SignIn, inclusive = false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    private fun navigateToSignUp() {
        resetState()
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.SignUp,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(route = Destination.SignIn, inclusive = false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    private fun signInUsernameChanged(value: String) {
        _state.update { state ->
            state.copy(
                username = value,
                signInEnabled = state.username.isNotEmpty() && state.password.isNotEmpty()
            )
        }
    }

    private fun signInPasswordChanged(value: String) {
        _state.update { state ->
            state.copy(
                password = value,
                signInEnabled = state.username.isNotEmpty() && state.password.isNotEmpty()
            )
        }
    }

    private fun togglePasswordVisibility() {
        _state.update { state ->
            state.copy(
                passwordVisibility = !state.passwordVisibility
            )
        }
    }

    private fun signInSubmit() {
        if (onSignInError()) return
        viewModelScope.launch {
            val call = authRepository.signIn(
                signInDto = SignInDto(
                    username = _state.value.username,
                    password = _state.value.password
                )
            )
            when (call) {
                is RequestState.Error -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = call.error.message
                        )
                    }
                    messages.sendMessage(call.error)
                }
                is RequestState.Success -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    messages.sendMessage(call.data)
                    navigator.navigate(
                        destination = Destination.Home,
                        navOptions = NavOptions.Builder().apply {
                            setPopUpTo(route = Destination.AuthGraph, inclusive = true)
                            setLaunchSingleTop(true)
                        }.build()
                    )

                    resetState()
                }
                else -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun onSignInError(): Boolean {
        val validation = AuthValidator.validateSignIn(
            signIn = SignIn(
                username = _state.value.username,
                password = _state.value.password,
            )
        )
        val errors = listOfNotNull(
            validation.usernameError,
            validation.passwordError
        )
        _state.update { state ->
            state.copy(
                usernameError = validation.usernameError,
                passwordError = validation.passwordError
            )
        }
        return errors.isNotEmpty()
    }

    private fun resetState() {
        _state.update { state ->
            state.copy(
                username = "",
                password = "",
                usernameError = null,
                passwordError = null,
                passwordVisibility = false,
                signInEnabled = false
            )
        }
    }
}
