package com.example.myapplication.profile.currency.currencyRetrofit

class CurrencyRepository {
    suspend fun getRates(): CurrencyModel {
        return RetrofitHelper.api.getRates()
    }
}