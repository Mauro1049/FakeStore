package com.example.fakestore.data

import com.example.fakestore.model.LoginRequest
import com.example.fakestore.model.LoginResponse
import com.example.fakestore.model.ProductsModel
import com.example.fakestore.model.ShoppingCartModel
import com.example.fakestore.model.UserModel
import com.example.fakestore.util.Constants.Companion.ENDPOINTCART
import com.example.fakestore.util.Constants.Companion.ENDPOINTCATEGORIES
import com.example.fakestore.util.Constants.Companion.ENDPOINTLOGIN
import com.example.fakestore.util.Constants.Companion.ENDPOINTPRODUCTS
import com.example.fakestore.util.Constants.Companion.ENDPOINTPRODUCTSCATEGORIES
import com.example.fakestore.util.Constants.Companion.ENDPOINTUSER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIFakeStore {

    /*
    * Esta funcion realiza un POST al endpoint de login de la API,
    * recibe un objeto LoginRequest y devuelve un objeto LoginResponse
    * */
    @POST(ENDPOINTLOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    /*
    * Esta funcion realiza un GET al endpoint de productos de la API, y recuperea la lista de productos.
    * */
    //Products
    @GET(ENDPOINTPRODUCTS)
    suspend fun getProducts(): Response<List<ProductsModel>>

    /*
    * Esta funcion realiza un GET al endpoint de productos por id de la API, y recuperea un producto por id
    * */
    //Products by id
    @GET("$ENDPOINTPRODUCTS/{id}")
    suspend fun getProductById(@Path(value = "id")id: Int): Response<ProductsModel>

    /*
    * Esta funcion realiza un GET al endpoint de categorias de la API, y recuperea la lista de categorias.
    * */
    //Categories
    @GET(ENDPOINTCATEGORIES)
    suspend fun getCategories(): Response<List<String>>

    /*
    * Esta funcion realiza un GET al endpoint de productos por categoria de la API,
    *  y recuperea la lista de productos por categoria
    * */
    //Productos por categoria
    @GET("$ENDPOINTPRODUCTSCATEGORIES/{category}")
    suspend fun getProdcutsByCategory(@Path(value = "category")category: String): Response<List<ProductsModel>>

    /*
    * Esta funcion realiza un GET al endpoint de usuario de la API, y recuperea un usuario por id de usuario
    * */
    //User
    @GET("$ENDPOINTUSER/{id}")
    suspend fun getUser(@Path(value = "id")id: Int): Response<UserModel>

    /*
    * Esta funcion realiza un GET al endpoint de carrito de la API, y recuperea la lista de productos en el carrito
    * */
    //Cart por usuario
    @GET("$ENDPOINTCART/{id}")
    suspend fun getCartByUser(@Path(value = "id")id: Int): Response<List<ShoppingCartModel>>
}