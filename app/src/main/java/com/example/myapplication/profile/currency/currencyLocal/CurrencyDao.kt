package com.example.myapplication.profile.currency.currencyLocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.profile.currency.models.Rates

@Dao
interface CurrencyDao {

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateBase(currencyLocalModel: CurrencyLocalModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateRates(rates: Rates)

    @Query("SELECT * FROM base_table")
    fun readAllDataBase(): LiveData<CurrencyLocalModel>

    @Query("SELECT * FROM rates_table")
    fun readAllDataRates(): LiveData<Rates>
}