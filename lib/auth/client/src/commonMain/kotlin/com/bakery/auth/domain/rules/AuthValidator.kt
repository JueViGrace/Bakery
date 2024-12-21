package com.bakery.auth.domain.rules

import com.bakery.auth.domain.model.SignIn
import com.bakery.auth.domain.model.SignUp
import org.jetbrains.compose.resources.StringResource

object AuthValidator {

    fun validateSignIn(signIn: SignIn): SignInValidationError {
        val result = SignInValidationError()

        return result
    }

    fun validateSignUp(signUp: SignUp): SignUpValidationError {
        val result = SignUpValidationError()

        return result
    }

    data class SignInValidationError(
        val usernameError: StringResource? = null,
        val passwordError: StringResource? = null,
    )

    data class SignUpValidationError(
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
    )
}