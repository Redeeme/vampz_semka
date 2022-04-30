package com.example.myapplication.profile.currency.currencyLocal

import androidx.lifecycle.LiveData
import com.example.myapplication.profile.currency.models.Rates

class CurrencyLocalRepository(private val currencyDao: CurrencyDao) {

    val readAllDataBase: LiveData<CurrencyLocalModel> = currencyDao.readAllDataBase()

    val readAllDataRates: LiveData<Rates> = currencyDao.readAllDataRates()

    suspend fun updateDataBase(currencyLocalModel: CurrencyLocalModel){
        currencyDao.updateBase(currencyLocalModel)
    }

    suspend fun updateDataRates(rates: Rates){
        currencyDao.updateRates(rates)
    }
}