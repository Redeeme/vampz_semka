package com.example.myapplication.shop

import com.example.myapplication.model.ProductModelClass

interface IProductClickListener {
    fun add(product: ProductModelClass, position: Int)
    fun minus(product: ProductModelClass, position: Int)
    fun cartButton(product: ProductModelClass, position: Int)
}