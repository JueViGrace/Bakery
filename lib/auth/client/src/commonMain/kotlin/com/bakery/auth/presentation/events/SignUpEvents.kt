package com.bakery.auth.presentation.events

sealed interface SignUpEvents {
    data class OnSignUpFirstNameChanged(val value: String) : SignUpEvents
    data class OnSignUpLastNameChanged(val value: String) : SignUpEvents
    data class OnSignUpUsernameChanged(val value: String) : SignUpEvents
    data class OnSignUpEmailChanged(val value: String) : SignUpEvents
    data class OnSignUpPasswordChanged(val value: String) : SignUpEvents
    data class OnSignUpPhoneNumberChanged(val value: String) : SignUpEvents
    data class OnSignUpBirthDateChanged(val value: String) : SignUpEvents
    data class OnSignUpAddress1Changed(val value: String) : SignUpEvents
    data class OnSignUpAddress2Changed(val value: String) : SignUpEvents
    data class OnSignUpGenderChanged(val value: String) : SignUpEvents
    data object OnSignUpSubmit : SignUpEvents
    data object OnNavigateToSignIn : SignUpEvents
}
