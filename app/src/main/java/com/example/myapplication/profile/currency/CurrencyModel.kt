package com.example.myapplication.profile.currency

import com.google.gson.annotations.SerializedName

data class CurrencyModel(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    @SerializedName("rates")
    val rates: Rates
)