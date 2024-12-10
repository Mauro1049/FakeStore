package com.example.fakestore.model

data class ShoppingCartModel(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<OrderProduct>,
    val v: Int
)

data class OrderProduct(
    val productId: Int,
    val quantity: Int
)