package com.example.myapplication.profile.currency.currencyRequest

class CurrencyRepository {
    suspend fun getRates(): CurrencyModel {
        return RetrofitHelper.api.getRates()
    }
}