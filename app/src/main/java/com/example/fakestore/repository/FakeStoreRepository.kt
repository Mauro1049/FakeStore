package com.example.fakestore.repository

import com.example.fakestore.data.APIFakeStore
import com.example.fakestore.model.LoginRequest
import com.example.fakestore.model.ProductsModel
import com.example.fakestore.model.ShoppingCartModel
import com.example.fakestore.model.UserModel
import retrofit2.Response
import javax.inject.Inject

class FakeStoreRepository @Inject constructor(private val apiFakeStore: APIFakeStore) {

    //Funcion para hacer login
    suspend fun login(username: String, password: String): String {
        val response = apiFakeStore.login(LoginRequest(username, password))
        return response.token
    }

    //Funcione para traer todos los productos de la API
    suspend fun getProducts(): List<ProductsModel>? {
        val response = apiFakeStore.getProducts()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    //Funcion para traer un producto por id
    suspend fun getProductById(id: Int): ProductsModel? {
        val response = apiFakeStore.getProductById(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    //Funcion para traer las categorias de la API
    suspend fun getCategories(): List<String>? {
        val response = apiFakeStore.getCategories()
        if(response.isSuccessful){
            return response.body()
        }
        return null
    }

    //Funcion para traer los productos por categoria
    suspend fun getProductsByCategory(category: String): List<ProductsModel>? {
        val response = apiFakeStore.getProdcutsByCategory(category)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    //Funcion para traer un usuario
    suspend fun getUser(id: Int): UserModel? {
        val response = apiFakeStore.getUser(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    //Funcion para traer el carrito de un usuario
    suspend fun getCartByUser(id: Int) : List<ShoppingCartModel>? {
        val response = apiFakeStore.getCartByUser(id)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
}