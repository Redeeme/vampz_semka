package com.example.myapplication.cart

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.OrderModelClass
import com.example.myapplication.model.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CartViewModel: ViewModel() {
    var data: MutableLiveData<ArrayList<ProductModelClass>> = MutableLiveData()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var orderPrice: MutableLiveData<Double> = MutableLiveData()
    var orderWeight: MutableLiveData<Double> = MutableLiveData()
    var orderSize: MutableLiveData<Int> = MutableLiveData()

    init {
        viewModelScope.launch {
            data.value = ArrayList(loadData())
            setData()
        }
    }

    private suspend fun loadData(): List<ProductModelClass> {
        return db.collection(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
            .documents.mapNotNull {
                it.toObject(ProductModelClass::class.java)
            }
    }

    private fun setData() {
        orderPrice.value = 0.0
        orderWeight.value = 0.0
        var price = 0.0
        var weight = 0.0
        for (item in data.value!!) {
            price += (item.productAmount * item.productPrice!!)
            weight += item.productAmount
        }
        orderPrice.value = price
        orderWeight.value = weight
        orderSize.value = data.value!!.size
    }

    fun minus(product: ProductModelClass) {
        if (product.productAmount > 0) {
            product.productAmount--
            db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", product.productAmount)
        }
    }

    fun add(product: ProductModelClass) {
        product.productAmount++
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", product.productAmount)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun cartButton(product: ProductModelClass,position: Int) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            .delete()
            .addOnSuccessListener { Log.d("firestore", "DocumentSnapshot successfully deleted!") }
            .addOnSuccessListener { Log.w("firestore", "Error deleting document") }
        data.value!!.removeAt(position)
        setData()
    }

    fun show(product: ProductModelClass): Bundle {
        return bundleOf("product" to product)
    }

    fun removeFromDb() {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var key: String = (document.data.getValue("productName").toString())
                    db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(key).delete()

                }
            }
        data.value!!.clear()
        setData()
    }
    private fun addOrder(order: OrderModelClass) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid + "+")
            .add(order)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkout() {
        setData()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = current.format(formatter)

        var order = OrderModelClass(0,FirebaseAuth.getInstance().currentUser!!.uid,formatted,orderPrice.toString(),orderSize.toString())
        addOrder(order)
        removeFromDb()

    }
}