package com.example.myapplication.profile.currency.currencyLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrencyLocalModel::class], version = 1, exportSchema = false)
@TypeConverters(MapTypeConverter::class)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object{
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getDatabase(context: Context): CurrencyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currency_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}