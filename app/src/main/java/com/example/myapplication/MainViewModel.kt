package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.currency.currencyLocal.CurrencyDatabase
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalModel
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import com.example.myapplication.profile.currency.currencyLocal.MapTypeConverter
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyModel
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CurrencyRepository, application: Application) :
    AndroidViewModel(application) {

    private val localRepo: CurrencyLocalRepository

    init {
        val currencyDao = CurrencyDatabase.getDatabase(application).currencyDao()

        localRepo = CurrencyLocalRepository(currencyDao)

        getRates()
    }

    private fun insertCurrency(currencyModel: CurrencyModel) {
        viewModelScope.launch(Dispatchers.IO) {

            localRepo.deleteDataBase()
            var mapTypeConverter = MapTypeConverter()
            localRepo.insertDataBase(
                CurrencyLocalModel(
                    currencyModel.success,
                    currencyModel.timestamp,
                    currencyModel.base,
                    currencyModel.date,
                    mapTypeConverter.RatestoMap(currencyModel.rates)!!
                )
            )

        }
    }

    private fun getRates() {
        viewModelScope.launch {
            val response = repository.getRates()
            insertCurrency(response)
        }
    }
}