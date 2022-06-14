package com.example.myapplication.profile.currency.currencyRetrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/*
Sluzi na vytvorenie singleton Retrofit API
*/
@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelper {
    private const val BASE_URL = "http://api.exchangeratesapi.io"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideCurrencyApiI(retrofit: Retrofit): CurrencyApiI = retrofit.create(CurrencyApiI::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: CurrencyApiI) = CurrencyRepository(apiService)
}