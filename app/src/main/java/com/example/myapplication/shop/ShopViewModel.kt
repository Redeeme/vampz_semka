package com.example.myapplication.shop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.product.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ShopViewModel: ViewModel() {
    var data: MutableLiveData<List<ProductModelClass>> = MutableLiveData()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        viewModelScope.launch {
            data.value = loadData().sortedBy { it.productClass }
        }
    }

     private suspend fun loadData(): List<ProductModelClass> {
        return db.collection("Shop").get().await()
                .documents.mapNotNull {
                    it.toObject(ProductModelClass::class.java)
                }
    }

    fun minus(product: ProductModelClass,position: Int) {
        if (product.productAmount > 0) {
            product.productAmount--
            data.value?.get(position)?.productAmount  = product.productAmount
        }
    }

    fun cartButton(product: ProductModelClass,context: Context) {
        if (product.productAmount >= 1) {
            val docIdRef: DocumentReference = db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            docIdRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        Toast.makeText(
                            context,
                            "product is already in cart :)",
                            Toast.LENGTH_SHORT
                        ).show()
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    if (document.data.getValue("productName") == product.productName!!) {
                                        var i: Int = ((document.data.getValue("productAmount")) as Number).toInt() + product.productAmount
                                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", i)
                                    }
                                }

                            }
                    } else {
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).set(product)
                        Toast.makeText(
                            context,
                            "${product.productName} have been added to cart.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.d("firestore", "Failed with: ", task.exception)
                }
            }
        } else {
            Toast.makeText(
                context,
                "You must choose atleast 1kg of ${product.productName} to add it.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun add(product: ProductModelClass,position: Int) {
        product.productAmount++
        data.value?.get(position)?.productAmount  = product.productAmount
    }

    fun show(product: ProductModelClass): Bundle {
        return  bundleOf("productName" to product.productName,
            "productOrigin" to product.productOrigin,
            "productClass" to product.productClass,
            "productImage" to product.productImage,
            "productPrice" to product.productPrice,
            "productAmount" to product.productAmount,
            "productInfo" to product.productInfo)
    }

}