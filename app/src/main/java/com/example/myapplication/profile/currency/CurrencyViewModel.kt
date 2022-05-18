package com.example.myapplication.profile.currency

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.profile.currency.currencyLocal.CurrencyDatabase
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalModel
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository

class CurrencyViewModel( application: Application) :
    AndroidViewModel(application) {


    val readAllDataBase: LiveData<CurrencyLocalModel>

    var baseList: CurrencyLocalModel? = null

    private val localRepo: CurrencyLocalRepository

    init {
        val currencyDao = CurrencyDatabase.getDatabase(application).currencyDao()

        localRepo = CurrencyLocalRepository(currencyDao)

        readAllDataBase = localRepo.readAllDataBase

    }



    fun updateCurrency() {
            Log.d("local", localRepo.readAllDataBase.value?.currencyMap.toString())
            for (cur in localRepo.readAllDataBase.value?.currencyMap!!.iterator()){
                Log.d("local", "${cur.key} : ${cur.value}")
            }

    }
    fun setBase(base: CurrencyLocalModel?){
        baseList = base
    }

    fun convert(currency: String){

    }
}