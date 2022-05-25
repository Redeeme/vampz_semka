package com.example.myapplication.profile.currency.currencyLocal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrencyLocalModel::class], version = 1, exportSchema = false)
@TypeConverters(MapTypeConverter::class)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun getCurrencyDao(): CurrencyDao

}

