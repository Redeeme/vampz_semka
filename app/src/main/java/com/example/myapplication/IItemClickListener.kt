package com.example.myapplication

import com.example.myapplication.Model.ProductModelClass

interface IItemClickListener {
    fun add(productModelClass: ProductModelClass, position: Int)
    fun minus(productModelClass: ProductModelClass, position: Int)
    fun addToCart(productModelClass: ProductModelClass, position: Int)
}