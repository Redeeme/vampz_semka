package com.example.myapplication.cart

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.OrderModelClass
import com.example.myapplication.product.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CartViewModel : ViewModel() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var cartData: MutableLiveData<ArrayList<ProductModelClass>> = MutableLiveData()
    val _cartData: LiveData<ArrayList<ProductModelClass>>
        get() = cartData

    private var orderPrice: MutableLiveData<Double> = MutableLiveData()
    val _orderPrice: LiveData<Double>
        get() = orderPrice

    private var orderWeight: MutableLiveData<Double> = MutableLiveData()
    val _orderWeight: LiveData<Double>
        get() = orderWeight

    private var orderSize: MutableLiveData<Int> = MutableLiveData()
    val _orderSize: LiveData<Int>
        get() = orderSize

    suspend fun update() {
        cartData.value = (
            ArrayList(db.collection(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
                .documents.mapNotNull {
                    it.toObject(ProductModelClass::class.java)
                })
        )
        setData()
    }


    private fun setData() {
        orderPrice.value = 0.0
        orderWeight.value = 0.0
        var price = 0.0
        var weight = 0.0
        for (item in cartData.value!!) {
            price += (item.productAmount * item.productPrice!!)
            weight += item.productAmount
        }
        orderPrice.value = price
        orderWeight.value = weight
        orderSize.value = cartData.value!!.size
    }

    fun minus(product: ProductModelClass) {
        if (product.productAmount > 0) {
            product.productAmount--
            db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                .document(product.productName!!).update("productAmount", product.productAmount)
            setData()
        }
    }

    fun add(product: ProductModelClass) {
        product.productAmount++
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            .update("productAmount", product.productAmount)
        setData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun cartButton(product: ProductModelClass, position: Int) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            .delete()
            .addOnSuccessListener { Log.d("firestore", "DocumentSnapshot successfully deleted!") }
            .addOnSuccessListener { Log.w("firestore", "Error deleting document") }
        cartData.value!!.removeAt(position)
        setData()
    }

    fun show(product: ProductModelClass): Bundle {
        return bundleOf("product" to product)
    }

    private fun removeFromDb() {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var key: String = (document.data.getValue("productName").toString())
                    db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(key)
                        .delete()

                }
            }
        cartData.value!!.clear()
        setData()
    }

    private fun addOrder(order: OrderModelClass) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid + "+")
            .add(order)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkout() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = current.format(formatter)

        val order = OrderModelClass(
            0,
            FirebaseAuth.getInstance().currentUser!!.uid,
            formatted,
            orderPrice.toString(),
            orderSize.toString()
        )
        addOrder(order)
        removeFromDb()
        setData()
    }
}