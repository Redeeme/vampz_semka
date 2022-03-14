package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application):AndroidViewModel(application) {

    private val readAllUserData: LiveData<List<Order>>
    private val repository: OrderRepository
    init {
        val orderDao = OrderDatabase.getDatabase(application).orderDao()
        repository = OrderRepository(orderDao)
        readAllUserData = repository.readAllUserData
    }
    fun addOrder(order: Order){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
        }
    }
}