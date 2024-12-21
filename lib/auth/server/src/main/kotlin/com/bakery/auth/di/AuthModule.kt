package com.bakery.auth.di

import com.bakery.auth.data.handler.AuthHandler
import com.bakery.auth.data.handler.DefaultAuthHandler
import com.bakery.auth.data.storage.AuthStore
import com.bakery.auth.data.storage.DefaultAuthStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthStore) bind AuthStore::class

    singleOf(::DefaultAuthHandler) bind AuthHandler::class
}
