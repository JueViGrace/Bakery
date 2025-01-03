package com.bakery.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.bakery.user.presentation.state.UsersListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersListViewModel : ViewModel() {
    private val _state = MutableStateFlow(UsersListState())
    val state = _state.asStateFlow()
}
