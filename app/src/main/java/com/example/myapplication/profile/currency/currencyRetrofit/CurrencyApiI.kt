package com.example.myapplication.profile.currency.currencyRetrofit

import retrofit2.http.GET

/*
sluzi na vytvorenie API retrofitu
*/
interface CurrencyApiI {
    @GET("/latest?access_key=76e4a08298ec463e9ed701d02a8ff8a6&format=1")
    suspend fun getRates(): CurrencyModel
}