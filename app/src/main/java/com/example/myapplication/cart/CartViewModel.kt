package com.example.myapplication.cart

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.profile.orders.OrderModelClass
import com.example.myapplication.shop.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val db: FirebaseFirestore) : ViewModel() {

    private var cartData: MutableLiveData<ArrayList<ProductModelClass>> = MutableLiveData()
    val _cartData: LiveData<ArrayList<ProductModelClass>>
        get() = cartData

    private var orderPrice: MutableLiveData<Double> = MutableLiveData()
    val _orderPrice: LiveData<Double>
        get() = orderPrice

    private var orderWeight: MutableLiveData<Double> = MutableLiveData()
    val _orderWeight: LiveData<Double>
        get() = orderWeight

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
    }

    private fun addOrder(order: OrderModelClass) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid + "+")
            .add(order)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkout(context: Context) {
        if (cartData.value!!.isNotEmpty()) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val formatted = current.format(formatter)

            val order = OrderModelClass(
                FirebaseAuth.getInstance().currentUser!!.uid,
                formatted,
                "${orderPrice.value.toString()} €",
                "${orderWeight.value.toString()} kg"
            )
            addOrder(order)
            removeFromDb()
            setData()
            Toast.makeText(
                context,
                "Order has been sent.",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            Toast.makeText(
                context,
                "Add items first.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}