package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalModel
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import com.example.myapplication.profile.currency.currencyLocal.MapTypeConverter
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyModel
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(private val localRepo: CurrencyLocalRepository, private val retrofit: CurrencyRepository) :ViewModel()  {

    private fun insertCurrency(currencyModel: CurrencyModel) {
        viewModelScope.launch(Dispatchers.IO) {

            if (localRepo.readAllData()[0].date != currencyModel.date) {
                localRepo.deleteDataBase()
                val mapTypeConverter = MapTypeConverter()
                localRepo.insertDataBase(
                    CurrencyLocalModel(
                        currencyModel.success,
                        currencyModel.timestamp,
                        currencyModel.base,
                        currencyModel.date,
                        mapTypeConverter.RatestoMap(currencyModel.rates)!!
                    )
                )
                Log.d("delul", localRepo.readAllData()[0].date)
                Log.d("delulr", currencyModel.date)

            } else {
                Log.d("lul", localRepo.readAllData()[0].date)
                Log.d("lulr", currencyModel.date)
            }
        }
    }

    fun getRates() {
        viewModelScope.launch {
            val response = retrofit.getRates()
            insertCurrency(response)
        }
    }
}