package com.example.myapplication.profile.currency.currencyLocal

import androidx.lifecycle.LiveData

class CurrencyLocalRepository(private val currencyDao: CurrencyDao) {

    val readAllDataBase: LiveData<CurrencyLocalModel> = currencyDao.readAllDataBase()

    suspend fun insertDataBase(currencyLocalModel: CurrencyLocalModel){
        currencyDao.insertBase(currencyLocalModel)
    }

    suspend fun updateDataBase(currencyLocalModel: CurrencyLocalModel){
        currencyDao.updateBase(currencyLocalModel)
    }

    suspend fun deleteDataBase(){
        currencyDao.deleteBase()
    }
}