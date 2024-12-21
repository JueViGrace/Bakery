package com.bakery.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bakery.app.di.appModule
import com.bakery.app.presentation.ui.App
import com.bakery.core.di.KoinBuilder
import com.bakery.core.di.coreModule
import org.koin.dsl.koinApplication

fun main() = application {
    KoinBuilder(koinApplication())
        .addModule(coreModule())
        .addModule(appModule())
        .build()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Bakery and Deserts",
    ) {
        App()
    }
}
