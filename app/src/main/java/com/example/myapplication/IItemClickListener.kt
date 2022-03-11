package com.example.myapplication

import com.example.myapplication.Model.ProductModelClass

interface IItemClickListener {
    fun add(product: ProductModelClass, position: Int)
    fun minus(product: ProductModelClass, position: Int)
    fun addToCart(product: ProductModelClass, position: Int)
}