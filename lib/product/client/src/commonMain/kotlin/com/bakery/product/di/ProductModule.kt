package com.bakery.product.di

import com.bakery.product.data.repository.DefaultProductRepository
import com.bakery.product.data.repository.ProductRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun productModule(): Module = module {
    singleOf(::DefaultProductRepository) bind ProductRepository::class
}
