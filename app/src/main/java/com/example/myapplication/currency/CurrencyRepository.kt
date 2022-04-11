package com.example.myapplication.currency

import com.example.myapplication.currency.util.Resource

interface CurrencyRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>
}