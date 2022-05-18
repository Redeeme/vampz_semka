package com.example.myapplication

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.profile.currency.CurrencyViewModel
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyRepository

class CurrencyViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(application) as T
    }
}

class MainViewModelFactory(private val repository: CurrencyRepository, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, application) as T
    }
}