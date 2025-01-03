package com.bakery.auth.presentation.state

import org.jetbrains.compose.resources.StringResource

data class SignUpState(
    // Field values
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val birthDate: String = "",
    val address1: String = "",
    val address2: String = "",
    val gender: String = "",

    // Errors messages
    val firstNameError: StringResource? = null,
    val lastNameError: StringResource? = null,
    val usernameError: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val phoneNumberError: StringResource? = null,
    val birthDateError: StringResource? = null,
    val address1Error: StringResource? = null,
    val address2Error: StringResource? = null,
    val genderError: StringResource? = null,
    val errorMessage: StringResource? = null,

    // Screen state
    val isLoading: Boolean = false,
)
