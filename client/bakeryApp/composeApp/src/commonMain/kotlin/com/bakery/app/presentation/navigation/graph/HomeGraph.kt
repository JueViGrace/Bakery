package com.bakery.app.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bakery.core.presentation.navigation.Destination
import com.bakery.order.presentation.ui.screens.OrderDetailsScreen
import com.bakery.order.presentation.ui.screens.OrdersListScreen
import com.bakery.product.presentation.ui.screens.ProductDetailsScreen
import com.bakery.product.presentation.ui.screens.ProductsListScreen
import com.bakery.user.presentation.ui.screens.UserDetailsScreen
import com.bakery.user.presentation.ui.screens.UsersListScreen

fun NavGraphBuilder.homeGraph() {
    navigation<Destination.HomeGraph>(
        startDestination = Destination.Home
    ) {
        composable<Destination.Home> {
        }

        composable<Destination.Profile> {
        }

        composable<Destination.Settings> {
        }

        composable<Destination.Users> {
            UsersListScreen()
        }

        composable<Destination.UserDetails> {
            UserDetailsScreen()
        }

        composable<Destination.Products> {
            ProductsListScreen()
        }

        composable<Destination.ProductDetails> {
            ProductDetailsScreen()
        }

        composable<Destination.Notifications> {
        }

        composable<Destination.Cart> {
        }

        composable<Destination.Checkout> {
        }

        composable<Destination.Orders> {
            OrdersListScreen()
        }

        composable<Destination.OrderDetails> {
            OrderDetailsScreen()
        }
    }
}
