package com.example.fakestore.state

import com.example.fakestore.model.RatingProduct

data class ProductState(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val image: String = "",
    val rating: RatingProduct = RatingProduct(0.0, 0)
)
