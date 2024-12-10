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

    //Login
    @POST(ENDPOINTLOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    //Products
    @GET(ENDPOINTPRODUCTS)
    suspend fun getProducts(): Response<List<ProductsModel>>

    //Products by id
    @GET("$ENDPOINTPRODUCTS/{id}")
    suspend fun getProductById(@Path(value = "id")id: Int): Response<ProductsModel>

    //Categories
    @GET(ENDPOINTCATEGORIES)
    suspend fun getCategories(): Response<List<String>>

    //Productos por categoria
    @GET("$ENDPOINTPRODUCTSCATEGORIES/{category}")
    suspend fun getProdcutsByCategory(@Path(value = "category")category: String): Response<List<ProductsModel>>

    //User
    @GET("$ENDPOINTUSER/{id}")
    suspend fun getUser(@Path(value = "id")id: Int): Response<UserModel>

    //Cart por usuario
    @GET("$ENDPOINTCART/{id}")
    suspend fun getCartByUser(@Path(value = "id")id: Int): Response<List<ShoppingCartModel>>
}