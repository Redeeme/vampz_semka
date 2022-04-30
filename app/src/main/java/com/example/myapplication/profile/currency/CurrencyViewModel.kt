package com.example.myapplication.profile.currency

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.currency.currencyLocal.CurrencyDatabase
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalModel
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import com.example.myapplication.profile.currency.currencyRequest.CurrencyModel
import com.example.myapplication.profile.currency.currencyRequest.CurrencyRepository
import com.example.myapplication.profile.currency.models.Rates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository,application: Application): AndroidViewModel(application) {

    val ratesXResponse: MutableLiveData<CurrencyModel> = MutableLiveData()

    private val readAllDataBase: LiveData<CurrencyLocalModel>
    private val readAllDataRates: LiveData<Rates>

    private val localRepo: CurrencyLocalRepository

    init {
        val currencyDao = CurrencyDatabase.getDatabase(application).currencyDao()

        localRepo = CurrencyLocalRepository(currencyDao)

        readAllDataBase = localRepo.readAllDataBase
        readAllDataRates = localRepo.readAllDataRates

    }

    fun updateCurrency(currencyModel: CurrencyModel){
        viewModelScope.launch(Dispatchers.IO){
            localRepo.updateDataBase(CurrencyLocalModel(currencyModel.success,
                currencyModel.timestamp,
                currencyModel.base,
                currencyModel.date))
            localRepo.updateDataRates(currencyModel.rates)
        }
    }

    fun getRates(){
        viewModelScope.launch {
            val response = repository.getRates()
            ratesXResponse.value = response
            updateCurrency(response)
        }
    }
}