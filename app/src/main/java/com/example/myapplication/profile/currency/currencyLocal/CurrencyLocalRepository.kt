package com.example.myapplication.profile.currency.currencyLocal

import javax.inject.Inject

class CurrencyLocalRepository @Inject constructor(private val currencyDao: CurrencyDao) {

    suspend fun insertDataBase(currencyLocalModel: CurrencyLocalModel){
        currencyDao.insertBase(currencyLocalModel)
    }

    suspend fun updateDataBase(currencyLocalModel: CurrencyLocalModel){
        currencyDao.updateBase(currencyLocalModel)
    }

    suspend fun deleteDataBase(){
        currencyDao.deleteBase()
    }
    suspend fun changeBase(base:String){
        currencyDao.changeBase(base)
    }

    fun readAllData():List<CurrencyLocalModel>{
        return currencyDao.readAllDataBase()
    }
}