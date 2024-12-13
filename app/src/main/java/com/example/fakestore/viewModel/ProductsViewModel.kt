package com.example.fakestore.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.model.ProductsModel
import com.example.fakestore.model.RatingProduct
import com.example.fakestore.repository.FakeStoreRepository
import com.example.fakestore.state.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

//ViewModel para los productos desde la API inyectada por Hilt y Repository con Retrofit
@HiltViewModel
class ProductsViewModel @Inject constructor(private val repository: FakeStoreRepository) : ViewModel() {

    //Esta variable la usamos para almacenar los productos
    //que se obtienen de la API y se muestran en la vista de productos
    private val _products = MutableStateFlow<List<ProductsModel>>(emptyList())
    val products = _products.asStateFlow()

    //Esta variable la usamos para almacenar el estado del producto
    var state by mutableStateOf(ProductState())
        private set

    //Inicializamos el ViewModel y llamamos a la funcion getProducts()
    init {
        getProducts()
    }

    //Funcion para obtener los productos de la API y almacenarlos en la variable products del ViewModel
    private fun getProducts(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val results = repository.getProducts()
                _products.value = results ?: emptyList()
            }
        }
    }

    //Funcion para obtener un producto por id de la API y almacenarlos en la variable state del ViewModel
    fun getProductById(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repository.getProductById(id)
                state = state.copy(
                    id = result?.id ?: 0,
                    title = result?.title ?: "",
                    price = result?.price ?: 0.0,
                    description = result?.description ?: "",
                    category = result?.category ?: "",
                    image = result?.image ?: "",
                    rating = RatingProduct(result?.rating?.rate ?: 0.0, result?.rating?.count ?: 0)
                )
            }
        }
    }

    //Funcion para limpiar la variable state del ViewModel
    fun clean(){
        state = state.copy(
            id = 0,
            title = "",
            price = 0.0,
            description = "",
            category = "",
            image = "",
            rating = RatingProduct( 0.0,  0)
        )
    }
}