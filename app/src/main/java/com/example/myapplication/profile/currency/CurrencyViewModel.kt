package com.example.myapplication.profile.currency

import androidx.lifecycle.ViewModel
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val localRepo: CurrencyLocalRepository) : ViewModel(){

    //val readAllDataBase: LiveData<CurrencyLocalModel> = localRepo.readAllData()


    fun updateCurrency() {
          /*  Log.d("local", localRepo.readAllData().value?.currencyMap.toString())
            for (cur in localRepo.readAllData().value?.currencyMap!!.iterator()){
                Log.d("local", "${cur.key} : ${cur.value}")
            }*/

    }

    fun convert(currency: String){

    }
}