package com.example.myapplication.profile.currency.currencyLocal

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
sluzi na vytvorenie singleton Room databazy
*/

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, CurrencyDatabase::class.java, "currency_database").build() // The reason we can construct a database for the repo
    @Singleton
    @Provides
    fun provideCurrencyDao(db: CurrencyDatabase) = db.getCurrencyDao() // The reason we can implement a Dao for the database
}