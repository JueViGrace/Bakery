package com.bakery.core.di

import androidx.lifecycle.SavedStateHandle
import com.bakery.core.presentation.messages.DefaultMessages
import com.bakery.core.presentation.messages.Messages
import com.bakery.core.presentation.navigation.DefaultNavigator
import com.bakery.core.presentation.navigation.Destination
import com.bakery.core.presentation.navigation.Navigator
import org.koin.core.module.Module
import org.koin.dsl.module

fun presentationModule(): Module = module {
    single {
        SavedStateHandle()
    }

    single<Navigator> {
        DefaultNavigator(
            startDestination = Destination.Splash,
            stateHandle = get(),
        )
    }

    single<Messages> {
        DefaultMessages()
    }
}
