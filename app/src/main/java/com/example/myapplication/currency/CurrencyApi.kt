package com.example.myapplication.currency

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest?access_key=76e4a08298ec463e9ed701d02a8ff8a6&format=1")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}