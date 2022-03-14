package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
@Insert
    suspend fun addOrder(order: Order)

    @Query("SELECT * FROM orders")
    fun readAllUserData(): LiveData<List<Order>>
}