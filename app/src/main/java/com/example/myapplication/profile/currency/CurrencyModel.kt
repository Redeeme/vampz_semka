package com.example.myapplication.profile.currency

data class CurrencyModel(
    val base: String,
    val date: String,
    val rates: RatesX,
    val success: Boolean,
    val timestamp: Int
)