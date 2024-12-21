package com.bakery.user.di

import com.bakery.user.data.repository.DefaultUserRepository
import com.bakery.user.data.repository.UserRepository
import com.bakery.user.presentation.viewmodel.UserDetailsViewModel
import com.bakery.user.presentation.viewmodel.UsersListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun userModule(): Module = module {
    singleOf(::DefaultUserRepository) bind UserRepository::class

    viewModelOf(::UsersListViewModel)

    viewModelOf(::UserDetailsViewModel)
}
