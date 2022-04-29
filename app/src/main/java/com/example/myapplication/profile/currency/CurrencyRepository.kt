package com.example.myapplication.profile.currency

class CurrencyRepository {
    suspend fun getRates(): CurrencyModel{
        return RetrofitHelper.api.getRates()
    }
}