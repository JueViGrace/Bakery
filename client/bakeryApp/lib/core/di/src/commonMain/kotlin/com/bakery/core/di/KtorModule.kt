package com.bakery.core.di

import com.bakery.core.api.KtorClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun ktorModule(): Module = module {
    singleOf(::KtorClient)
}
