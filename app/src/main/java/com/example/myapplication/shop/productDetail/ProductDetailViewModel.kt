package com.example.myapplication.shop.productDetail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.shop.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
/*
logika presunu produktu do kosika
*/
class ProductDetailViewModel : ViewModel() {

    private var productData: MutableLiveData<ProductModelClass> = MutableLiveData()
    val _productData: LiveData<ProductModelClass>
        get() = productData

    fun setProduct(product: ProductModelClass){
        productData.value = product
    }

    fun setAmount(amount:Int){
        _productData.value?.productAmount = amount
    }

    fun cartButton(context: Context) {
        if (productData.value?.productAmount!! >= 1) {
            val db = FirebaseFirestore.getInstance()
            val docIdRef: DocumentReference = db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(productData.value?.productName!!)
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
                                    if (document.data.getValue("productName") == productData.value?.productName) {
                                        val i: Int = ((document.data.getValue("productAmount")) as Number).toInt() + productData.value?.productAmount!!
                                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(
                                            productData.value?.productName!!
                                        ).update("productAmount", i)
                                    }
                                }

                            }
                    } else {
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(productData.value?.productName!!).set(
                            productData.value!!
                        )
                        Toast.makeText(
                            context,
                            "${productData.value?.productName} have been added to cart.",
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
                "You must choose atleast 1kg of ${productData.value?.productName} to add it.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}