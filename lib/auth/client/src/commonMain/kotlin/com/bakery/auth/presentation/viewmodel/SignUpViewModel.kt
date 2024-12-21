package com.bakery.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.bakery.auth.data.repository.AuthRepository
import com.bakery.auth.domain.model.SignUp
import com.bakery.auth.domain.rules.AuthValidator
import com.bakery.auth.presentation.events.SignUpEvents
import com.bakery.auth.presentation.state.SignUpState
import com.bakery.core.presentation.messages.Messages
import com.bakery.core.presentation.navigation.Destination
import com.bakery.core.presentation.navigation.Navigator
import com.bakery.core.shared.types.auth.SignUpDto
import com.bakery.core.types.state.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val navigator: Navigator,
    private val messages: Messages,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignUpEvents) {
        when (event) {
            SignUpEvents.OnNavigateToSignIn -> navigateToSignIn()
            is SignUpEvents.OnSignUpFirstNameChanged -> signUpFirstNameChanged(event.value)
            is SignUpEvents.OnSignUpLastNameChanged -> signUpLastNameChanged(event.value)
            is SignUpEvents.OnSignUpUsernameChanged -> signUpUsernameChanged(event.value)
            is SignUpEvents.OnSignUpEmailChanged -> signUpEmailChanged(event.value)
            is SignUpEvents.OnSignUpPasswordChanged -> signUpPasswordChanged(event.value)
            is SignUpEvents.OnSignUpPhoneNumberChanged -> signUpPhoneNumberChanged(event.value)
            is SignUpEvents.OnSignUpBirthDateChanged -> signUpBirthDateChanged(event.value)
            is SignUpEvents.OnSignUpAddress1Changed -> signUpAddress1Changed(event.value)
            is SignUpEvents.OnSignUpAddress2Changed -> signUpAddress2Changed(event.value)
            is SignUpEvents.OnSignUpGenderChanged -> signUpGenderChanged(event.value)
            SignUpEvents.OnSignUpSubmit -> onSignUpSubmit()
        }
    }

    private fun navigateToSignIn() {
        resetState()
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.SignIn,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(route = Destination.SignUp, inclusive = true)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    private fun signUpFirstNameChanged(value: String) {
        _state.update { state ->
            state.copy(
                firstName = value
            )
        }
    }

    private fun signUpLastNameChanged(value: String) {
        _state.update { state ->
            state.copy(
                lastName = value
            )
        }
    }

    private fun signUpUsernameChanged(value: String) {
        _state.update { state ->
            state.copy(
                username = value
            )
        }
    }

    private fun signUpEmailChanged(value: String) {
        _state.update { state ->
            state.copy(
                email = value
            )
        }
    }

    private fun signUpPasswordChanged(value: String) {
        _state.update { state ->
            state.copy(
                password = value
            )
        }
    }

    private fun signUpPhoneNumberChanged(value: String) {
        _state.update { state ->
            state.copy(
                phoneNumber = value
            )
        }
    }

    private fun signUpBirthDateChanged(value: String) {
        _state.update { state ->
            state.copy(
                birthDate = value
            )
        }
    }

    private fun signUpAddress1Changed(value: String) {
        _state.update { state ->
            state.copy(
                address1 = value
            )
        }
    }

    private fun signUpAddress2Changed(value: String) {
        _state.update { state ->
            state.copy(
                address2 = value
            )
        }
    }

    private fun signUpGenderChanged(value: String) {
        _state.update { state ->
            state.copy(
                gender = value
            )
        }
    }

    private fun onSignUpSubmit() {
        if (onSignUpError()) return
        viewModelScope.launch {
            val call = authRepository.signUp(
                signUpDto = SignUpDto(
                    firstName = _state.value.firstName,
                    lastName = _state.value.lastName,
                    username = _state.value.username,
                    email = _state.value.email,
                    password = _state.value.password,
                    phoneNumber = _state.value.phoneNumber,
                    birthDate = _state.value.birthDate,
                    address1 = _state.value.address1,
                    address2 = _state.value.address2,
                    gender = _state.value.gender,
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

    private fun onSignUpError(): Boolean {
        val validation = AuthValidator.validateSignUp(
            signUp = SignUp(
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                username = _state.value.username,
                email = _state.value.email,
                password = _state.value.password,
                phoneNumber = _state.value.phoneNumber,
                birthDate = _state.value.birthDate,
                address1 = _state.value.address1,
                address2 = _state.value.address2,
                gender = _state.value.gender,
            )
        )
        val errors = listOfNotNull(
            validation.firstNameError,
            validation.lastNameError,
            validation.usernameError,
            validation.emailError,
            validation.passwordError,
            validation.phoneNumberError,
            validation.birthDateError,
            validation.address1Error,
            validation.address2Error,
            validation.genderError
        )
        _state.update { state ->
            state.copy(
                firstNameError = validation.firstNameError,
                lastNameError = validation.lastNameError,
                usernameError = validation.usernameError,
                emailError = validation.emailError,
                passwordError = validation.passwordError,
                phoneNumberError = validation.phoneNumberError,
                birthDateError = validation.birthDateError,
                address1Error = validation.address1Error,
                address2Error = validation.address2Error,
                genderError = validation.genderError
            )
        }
        return errors.isNotEmpty()
    }

    private fun resetState() {
        _state.update { state ->
            state.copy(
                firstName = "",
                lastName = "",
                username = "",
                email = "",
                password = "",
                phoneNumber = "",
                birthDate = "",
                address1 = "",
                address2 = "",
                gender = "",
                firstNameError = null,
                lastNameError = null,
                usernameError = null,
                emailError = null,
                passwordError = null,
                phoneNumberError = null,
                birthDateError = null,
                address1Error = null,
                address2Error = null,
                genderError = null,
            )
        }
    }
}
