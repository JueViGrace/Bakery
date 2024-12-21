package com.bakery.core.api.di

import com.bakery.core.util.Jwt
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun utilModule(): Module = module {
    singleOf(::Jwt)
}
