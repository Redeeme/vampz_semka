package com.example.myapplication.profile.currency.currencyLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_table")
data class CurrencyLocalModel(
    val success: Boolean,
    @PrimaryKey
    val timestamp: Int,
    val base: String,
    val date: String
)

