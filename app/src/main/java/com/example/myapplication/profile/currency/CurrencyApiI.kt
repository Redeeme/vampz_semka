package com.example.myapplication.profile.currency

import retrofit2.http.GET

interface CurrencyApiI {

    @GET("/latest?access_key=76e4a08298ec463e9ed701d02a8ff8a6&format=1")
    suspend fun getRates(): CurrencyModel
}