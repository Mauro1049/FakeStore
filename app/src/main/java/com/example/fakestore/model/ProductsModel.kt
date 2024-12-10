package com.example.fakestore.model

data class ProductsModel(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingProduct
)

data class RatingProduct(
    val rate: Double,
    val count: Int
)
