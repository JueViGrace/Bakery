package com.bakery.core.api.di

import com.bakery.core.database.driver.DriverFactory
import com.bakery.core.database.helper.DbHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun databaseModule(): Module = module {
    singleOf(::DriverFactory)

    singleOf(::DbHelper)
}
