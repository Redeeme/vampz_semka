package com.example.myapplication.profile.currency.currencyLocal

import androidx.room.*

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBase(currencyLocalModel: CurrencyLocalModel)

    @Update
    suspend fun updateBase(currencyLocalModel: CurrencyLocalModel)

    @Query("DELETE FROM base_table")
    suspend fun deleteBase()

    @Query("SELECT * FROM base_table")
    fun readAllDataBase(): List<CurrencyLocalModel>
}