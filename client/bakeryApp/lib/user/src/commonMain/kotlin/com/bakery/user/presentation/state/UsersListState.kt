package com.bakery.user.presentation.state

import com.bakery.core.types.user.User

data class UsersListState(
    val users: List<User> = emptyList(),
)
