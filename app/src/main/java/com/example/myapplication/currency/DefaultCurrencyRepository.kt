package com.example.myapplication.currency

import com.example.myapplication.currency.util.Resource
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        } catch (e :Exception){
            Resource.Error(e.message ?: "An error occured")
        }
    }
}