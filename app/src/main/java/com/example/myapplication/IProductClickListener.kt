package com.example.myapplication

import com.example.myapplication.product.ProductModelClass

interface IProductClickListener {
    fun add(product: ProductModelClass, position: Int)
    fun minus(product: ProductModelClass, position: Int)
    fun cartButton(product: ProductModelClass, position: Int)
    fun show(product: ProductModelClass)
}