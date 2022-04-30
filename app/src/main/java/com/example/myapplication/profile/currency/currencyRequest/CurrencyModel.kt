package com.example.myapplication.profile.currency.currencyRequest

import androidx.room.Entity
import com.example.myapplication.profile.currency.models.Rates

data class CurrencyModel(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Rates
)