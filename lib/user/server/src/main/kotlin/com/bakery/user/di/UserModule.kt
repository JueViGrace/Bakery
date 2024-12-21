package com.bakery.user.di

import com.bakery.user.data.handler.DefaultUserHandler
import com.bakery.user.data.handler.UserHandler
import com.bakery.user.data.storage.DefaultUserStorage
import com.bakery.user.data.storage.UserStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun userModule(): Module = module {
    singleOf(::DefaultUserStorage) bind UserStorage::class

    singleOf(::DefaultUserHandler) bind UserHandler::class
}
