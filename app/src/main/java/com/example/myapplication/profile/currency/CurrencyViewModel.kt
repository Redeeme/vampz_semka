package com.example.myapplication.profile.currency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val localRepo: CurrencyLocalRepository) : ViewModel(){

    //val readAllDataBase: LiveData<CurrencyLocalModel> = localRepo.readAllData()


    fun updateCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("lulw", localRepo.readAllData().get(0).currencyMap.toString())

        }

    }

    fun convert(currency: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("lulw", localRepo.readAllData().get(0).base)
            localRepo.changeBase(currency)
            Log.d("lulw", localRepo.readAllData().get(0).base)
        }
    }
}