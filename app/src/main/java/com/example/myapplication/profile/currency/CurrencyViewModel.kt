package com.example.myapplication.profile.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository): ViewModel() {

    val ratesXResponse: MutableLiveData<CurrencyModel> = MutableLiveData()

    fun getRates(){
        viewModelScope.launch {
            val response = repository.getRates()
            ratesXResponse.value = response
        }
    }
}