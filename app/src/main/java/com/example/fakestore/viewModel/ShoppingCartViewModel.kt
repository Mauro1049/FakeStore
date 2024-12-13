package com.example.fakestore.viewModel

import androidx.lifecycle.ViewModel
import com.example.fakestore.model.ShoppingCartModel
import com.example.fakestore.repository.FakeStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

//ViewModel para el carrito de compras desde la API inyectada por Hilt y Repository con Retrofit
@HiltViewModel
class ShoppingCartViewModel @Inject constructor(private val repository: FakeStoreRepository) : ViewModel() {

    //Esta variable la usamos para almacenar los productos del carrito de compras
    // que se obtienen de la API y se muestran en la vista
    private val _cart = MutableStateFlow<List<ShoppingCartModel>>(emptyList())
    val cart = _cart.asStateFlow()

    //Funcion para obtener los productos del carrito de compras de la API y almacenarlos en la variable cart del ViewModel
    suspend fun getCartByUser(id: Int) {
        val result = repository.getCartByUser(id)
        _cart.value = result ?: emptyList()
    }
}