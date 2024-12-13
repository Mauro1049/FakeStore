package com.example.fakestore.repository

import com.example.fakestore.data.APIFakeStore
import com.example.fakestore.model.LoginRequest
import com.example.fakestore.model.ProductsModel
import com.example.fakestore.model.ShoppingCartModel
import com.example.fakestore.model.UserModel
import retrofit2.Response
import javax.inject.Inject

class FakeStoreRepository @Inject constructor(private val apiFakeStore: APIFakeStore) {

    /*
    * Esta funcion realiza un POST al endpoint de login de la API,
    *  recibe un objeto LoginRequest y devuelve un objeto LoginResponse
    * */
    suspend fun login(username: String, password: String): String {
        val response = apiFakeStore.login(LoginRequest(username, password))
        return response.token
    }

    /*
    * Esta funcion realiza un GET al endpoint de productos de la API, y recuperea la lista de productos.
    * */
    suspend fun getProducts(): List<ProductsModel>? {
        val response = apiFakeStore.getProducts()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    /*
    * Esta funcion realiza un GET al endpoint de productos por id de la API, y recuperea un producto por id
    * */
    suspend fun getProductById(id: Int): ProductsModel? {
        val response = apiFakeStore.getProductById(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    /*
    * Esta funcion realiza un GET al endpoint de categorias de la API, y recuperea la lista de categorias.
    * */
    suspend fun getCategories(): List<String>? {
        val response = apiFakeStore.getCategories()
        if(response.isSuccessful){
            return response.body()
        }
        return null
    }

    /*
    * Esta funcion realiza un GET al endpoint de productos por categoria de la API
    * */
    suspend fun getProductsByCategory(category: String): List<ProductsModel>? {
        val response = apiFakeStore.getProdcutsByCategory(category)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    /*
    * Esta funcion realiza un GET al endpoint de usuario de la API, y recuperea un usuario por id de usuario
    * */
    suspend fun getUser(id: Int): UserModel? {
        val response = apiFakeStore.getUser(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    /*
    * Esta funcion realiza un GET al endpoint de carrito de la API, y recuperea un carrito por id de usuario.
    * */
    suspend fun getCartByUser(id: Int) : List<ShoppingCartModel>? {
        val response = apiFakeStore.getCartByUser(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
}