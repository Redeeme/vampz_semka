package com.example.myapplication.model

data class ProductModelClass (
        val productName: String? = "",
        val productOrigin: String? = "",
        val productClass: String? = "",
        val productImage: Int = 0,
        val productPrice: Double? = 0.0,
        var productAmount: Int = 0,
        val productInfo: String? = ""
)