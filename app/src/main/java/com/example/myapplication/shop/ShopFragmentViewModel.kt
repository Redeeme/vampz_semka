package com.example.myapplication.shop

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.product.ProductModelClass
import com.google.firebase.firestore.*

class ShopFragmentViewModel : ViewModel() {
    //var shopItemList: ArrayList<ProductModelClass> = ArrayList()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _shopItemList = MutableLiveData<ArrayList<ProductModelClass>>()
    val shopItemList: LiveData<ArrayList<ProductModelClass>>
        get() = _shopItemList

    fun loadData() {

        db.collection("Shop").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        _shopItemList.value?.add((dc.document.toObject(ProductModelClass::class.java)))
                    }
                    println(_shopItemList.value?.get(0))
                }

               /* shopItemList.sortedBy {
                        product -> product.productClass
                }*/
            }

        })

    }

}