package com.example.myapplication.profile.currency.currencyRetrofit

class CurrencyRepository(private val currencyApiI: CurrencyApiI) {
    suspend fun getRates() = currencyApiI.getRates()
}