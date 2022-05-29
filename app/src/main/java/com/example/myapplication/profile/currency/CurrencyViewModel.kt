package com.example.myapplication.profile.currency

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.currency.currencyLocal.CurrencyLocalRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val localRepo: CurrencyLocalRepository,
                                            private val firebase: FirebaseFirestore
) : ViewModel(){

    //val readAllDataBase: LiveData<CurrencyLocalModel> = localRepo.readAllData()
    private var currentCurr: MutableLiveData<String> = MutableLiveData()
    val _currentCurr: LiveData<String>
        get() = currentCurr

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentCurr.postValue(localRepo.readAllData().get(0).base)
        }
    }

    fun convert(currency: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("lulw", localRepo.readAllData().get(0).base)

            localRepo.changeBase(currency)
            firebase.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val key: String = (document.data.getValue("productName").toString())
                        firebase.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                            .document(key)
                            .delete()
                    }
                }
            currentCurr.postValue(currency)
            Log.d("lulw", localRepo.readAllData().get(0).base)
        }
    }
}