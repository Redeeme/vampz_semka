package com.example.myapplication.cart

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.product.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class CartFragmentViewModel: ViewModel() {
    private lateinit var db: FirebaseFirestore
    var money: Double = 0.0
    var weight: Int = 0

    val data: LiveData<ArrayList<ProductModelClass>>
        get() = _data
    private val _data = MutableLiveData<ArrayList<ProductModelClass>>()

    init {
        loadData()
    }

    private fun loadData() {
        var cartItemList = ArrayList<ProductModelClass>()
        db = FirebaseFirestore.getInstance()
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        cartItemList.add(dc.document.toObject(ProductModelClass::class.java))
                    }
                }
                _data.postValue(cartItemList)
                setData()
            }

        })
    }
    private fun setData(){
        for (item in data.value!!) {
            money += (item.productAmount * item.productPrice!!)
            weight += item.productAmount
        }
    }
    fun minus(product: ProductModelClass, position: Int){
        if (product.productAmount > 0) {
            product.productAmount--
            itemAdapter.notifyItemChanged(position)
            db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", product.productAmount)
        }
    }

}
























