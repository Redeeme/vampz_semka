package com.example.myapplication.profile.currency

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.profile.currency.currencyRequest.CurrencyRepository

class CurrencyViewModelFactory(private val repository: CurrencyRepository, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository, application) as T
    }

}