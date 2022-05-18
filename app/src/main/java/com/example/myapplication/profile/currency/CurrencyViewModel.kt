package com.example.myapplication.profile.currency

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.currency.currencyLocal.CurrencyDatabase
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalModel
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import com.example.myapplication.profile.currency.currencyLocal.MapTypeConverter
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyModel
import com.example.myapplication.profile.currency.currencyRetrofit.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository, application: Application) :
    AndroidViewModel(application) {

    val ratesXResponse: MutableLiveData<CurrencyModel> = MutableLiveData()

    val readAllDataBase: LiveData<CurrencyLocalModel>

    var baseList: CurrencyLocalModel? = null

    private val localRepo: CurrencyLocalRepository

    init {
        val currencyDao = CurrencyDatabase.getDatabase(application).currencyDao()

        localRepo = CurrencyLocalRepository(currencyDao)

        readAllDataBase = localRepo.readAllDataBase

    }

    fun insertCurrency(currencyModel: CurrencyModel) {
        viewModelScope.launch(Dispatchers.IO) {

            localRepo.deleteDataBase()
            var mapTypeConverter = MapTypeConverter()
            localRepo.insertDataBase(
                CurrencyLocalModel(
                    currencyModel.success,
                    currencyModel.timestamp,
                    currencyModel.base,
                    currencyModel.date,
                    mapTypeConverter.RatestoMap(currencyModel.rates)!!
                )
            )

        }
    }

    fun getRates() {
        viewModelScope.launch {
            val response = repository.getRates()
            ratesXResponse.value = response
            insertCurrency(response)
        }
    }

    fun updateCurrency() {
        if (ratesXResponse.value?.date == baseList?.date) {
            Log.d("local", localRepo.readAllDataBase.value?.currencyMap.toString())
            for (cur in localRepo.readAllDataBase.value?.currencyMap!!.iterator()){
                Log.d("local", "${cur.key} : ${cur.value}")
            }
        }
    }
    fun setBase(base: CurrencyLocalModel?){
        baseList = base
    }

    fun convert(currency: String){

    }
}