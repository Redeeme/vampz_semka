package com.example.myapplication.profile.currency.currencyRetrofit

/*
Sluzi na vytvorenie repozitara retrofit pomocou API
*/
class CurrencyRepository(private val currencyApiI: CurrencyApiI) {
    suspend fun getRates() = currencyApiI.getRates()
}