package com.example.myapplication.profile.currency.currencyRetrofit

import com.example.myapplication.profile.currency.models.Rates

/*
sluzi ako model citania jsonu pre retrofit
*/
data class CurrencyModel(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Rates
)