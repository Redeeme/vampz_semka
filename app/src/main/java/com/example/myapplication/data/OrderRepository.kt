package com.example.myapplication.data

import androidx.lifecycle.LiveData

class OrderRepository(private val orderDao: OrderDao){
    val readAllUserData: LiveData<List<Order>> = orderDao.readAllUserData()
    suspend fun addOrder(order:Order){
        orderDao.addOrder(order)
    }
}