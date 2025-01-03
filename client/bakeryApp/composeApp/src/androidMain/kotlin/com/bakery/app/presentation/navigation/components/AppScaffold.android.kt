package com.bakery.app.presentation.navigation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bakery.app.presentation.navigation.graph.authGraph
import com.bakery.app.presentation.navigation.graph.homeGraph
import com.bakery.app.splash.presentation.ui.screen.SplashScreen
import com.bakery.core.presentation.navigation.Destination
import com.bakery.core.presentation.navigation.Navigator
import com.bakery.core.presentation.ui.components.buttons.BackArrowButton
import com.bakery.core.presentation.ui.components.layout.TopBarComponent
import kotlinx.coroutines.launch

// TODO: limit access to screens
@Composable
actual fun AppScaffold(
    modifier: Modifier,
    navController: NavHostController,
    navigator: Navigator,
    snackBarHostState: SnackbarHostState,
) {
    val stack by navigator.stack.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            when (stack.currentDestination) {
                Destination.AuthGraph -> { }
                Destination.Cart -> { }
                Destination.Checkout -> { }
                Destination.ForgotPassword -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        },
                    )
                }

                Destination.Home -> { }
                Destination.HomeGraph -> { }
                Destination.Notifications -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        },
                    )
                }

                is Destination.OrderDetails -> { }
                Destination.Orders -> { }
                is Destination.ProductDetails -> { }
                Destination.Products -> { }
                Destination.Profile -> { }
                Destination.Settings -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        },
                    )
                }

                Destination.SignIn -> { }
                Destination.SignUp -> { }
                Destination.Splash -> { }
                is Destination.UserDetails -> {}
                Destination.Users -> {}
                null -> { }
            }
        },
        bottomBar = {
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = navigator.startDestination,
        ) {
            composable<Destination.Splash> {
                SplashScreen()
            }
            authGraph()
            homeGraph()
        }
    }
}
