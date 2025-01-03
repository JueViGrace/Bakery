package com.bakery.core.presentation.ui.components.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.bakery.core.presentation.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun BackHandlerComponent(navigator: Navigator){
    val scope = rememberCoroutineScope()
    BackHandler {
        scope.launch(Dispatchers.Main.immediate) {
            awaitFrame()
            navigator.navigateUp()
        }
    }
}
