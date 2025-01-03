package com.bakery.app.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bakery.auth.presentation.ui.screens.ForgotPasswordScreen
import com.bakery.auth.presentation.ui.screens.SignInScreen
import com.bakery.auth.presentation.ui.screens.SignUpScreen
import com.bakery.core.presentation.navigation.Destination

fun NavGraphBuilder.authGraph() {
    navigation<Destination.AuthGraph>(
        startDestination = Destination.SignIn
    ) {
        composable<Destination.SignIn> {
            SignInScreen()
        }

        composable<Destination.SignUp> {
            SignUpScreen()
        }

        composable<Destination.ForgotPassword> {
            ForgotPasswordScreen()
        }
    }
}
