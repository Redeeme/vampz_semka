package com.example.myapplication.profile.currency.currencyLocal

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBase(currencyLocalModel: CurrencyLocalModel)

    @Update
    suspend fun updateBase(currencyLocalModel: CurrencyLocalModel)

    @Query("DELETE FROM base_table")
    fun deleteBase()

    @Query("SELECT * FROM base_table ORDER BY timestamp DESC")
    fun readAllDataBase(): LiveData<CurrencyLocalModel>
}