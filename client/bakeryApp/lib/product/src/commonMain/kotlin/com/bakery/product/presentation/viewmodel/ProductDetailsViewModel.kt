package com.bakery.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.bakery.product.presentation.state.ProductDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailsState())
    val state = _state.asStateFlow()
}
