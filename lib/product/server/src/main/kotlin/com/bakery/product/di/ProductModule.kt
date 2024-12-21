package com.bakery.product.di

import com.bakery.product.data.handler.DefaultProductHandler
import com.bakery.product.data.handler.ProductHandler
import com.bakery.product.data.storage.DefaultProductStorage
import com.bakery.product.data.storage.ProductStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun productModule(): Module = module {
    singleOf(::DefaultProductHandler) bind ProductHandler::class

    singleOf(::DefaultProductStorage) bind ProductStorage::class
}
