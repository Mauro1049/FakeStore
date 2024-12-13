package com.example.fakestore.viewModel

import androidx.compose.runtime.LongState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.model.ProductsModel
import com.example.fakestore.repository.FakeStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//ViewModel para las categorias y los productos por categoria
// desde la API inyectada por Hilt y Repository con Retrofit
@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository: FakeStoreRepository): ViewModel(){

    /*
    * Esta variable la usamos para almacenar las categorias
    * que se obtienen de la API y se muestran en la vista de categorias
    * */
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories =  _categories.asStateFlow()

    /*
    * Esta variable la usamos para almacenar los productos
    * que se obtienen de la API y se muestran en la vista de productos
    * */
    private val _products = MutableStateFlow<Map<String, List<ProductsModel>>>(emptyMap())
    val products = _products.asStateFlow()


    //Inicializamos el ViewModel y llamamos a la funcion getCategories()
    init {
        getCategories()
    }

    //Funcion para obtener las categorias de la API y almacenarlas en la variable categories del ViewModel
    private fun getCategories() {
        viewModelScope.launch {
                val result = repository.getCategories()
                _categories.value = result ?: emptyList()
        }
    }

    //Funcion para obtener los productos de la API por categoria y almacenarlos en la variable products del ViewModel
    fun getProductsByCategory(category: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repository.getProductsByCategory(category)
                _products.value = _products.value.toMutableMap().apply {
                    put(category, result ?: emptyList())
                }
            }
        }
    }
}