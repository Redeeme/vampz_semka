package com.example.myapplication.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ProductModelClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopFragmentViewModel : ViewModel() {
    var shopItemList: ArrayList<ProductModelClass> = ArrayList()
    //var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val itemsCollectionRef = Firebase.firestore.collection("shop")

    fun loadData() = CoroutineScope(Dispatchers.IO).launch {

        itemsCollectionRef.get().addOnCompleteListener{
            for (document in it.result){
                val product = document.toObject<ProductModelClass>()
                product?.let {
                    shopItemList.add(product)
                    Log.d("yo","Sadge ${document.data}")
                }
            }
        }

    }

}