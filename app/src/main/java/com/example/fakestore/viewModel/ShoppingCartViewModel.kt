package com.example.fakestore.viewModel

import androidx.lifecycle.ViewModel
import com.example.fakestore.model.ShoppingCartModel
import com.example.fakestore.repository.FakeStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(private val repository: FakeStoreRepository) : ViewModel() {

    private val _cart = MutableStateFlow<List<ShoppingCartModel>>(emptyList())
    val cart = _cart.asStateFlow()

    suspend fun getCartByUser(id: Int) {
        val result = repository.getCartByUser(id)
        _cart.value = result ?: emptyList()
    }


}