package com.bakery.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.bakery.product.presentation.state.ProductsListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductsListViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductsListState())
    val state = _state.asStateFlow()
}
