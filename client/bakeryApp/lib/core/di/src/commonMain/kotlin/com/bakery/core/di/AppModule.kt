package com.bakery.core.di

import org.koin.core.module.Module

fun coreModule(): List<Module> = listOf(
    databaseModule(),
    ktorModule(),
    coroutinesModule(),
    presentationModule(),
)
