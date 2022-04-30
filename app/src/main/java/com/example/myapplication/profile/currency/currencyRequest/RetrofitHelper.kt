package com.example.myapplication.profile.currency.currencyRequest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.exchangeratesapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CurrencyApiI by lazy {
        retrofit.create(CurrencyApiI::class.java)
    }
}