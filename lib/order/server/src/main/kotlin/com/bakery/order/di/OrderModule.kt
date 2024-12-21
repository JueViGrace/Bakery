package com.bakery.order.di

import com.bakery.order.data.handler.DefaultOrderHandler
import com.bakery.order.data.handler.OrderHandler
import com.bakery.order.data.storage.DefaultOrderStorage
import com.bakery.order.data.storage.OrderStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun orderModule(): Module = module {
    singleOf(::DefaultOrderStorage) bind OrderStorage::class

    singleOf(::DefaultOrderHandler) bind OrderHandler::class
}
