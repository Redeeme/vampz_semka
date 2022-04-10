package com.example.myapplication.firestore

import com.example.myapplication.product.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val TAG = "FirebaseRepository"

    val db = FirebaseFirestore.getInstance()

    val userID = FirebaseAuth.getInstance().currentUser!!.uid

    val shopCollectionReference = db.collection("Shop")

    val cartCollectionReference = db.collection(userID)

    val orderCollectionReference = db.collection("${userID}+")

    suspend fun isProductInCart(product: ProductModelClass): Boolean {
       return cartCollectionReference.document(product.productName!!).get().await().exists()
    }

    suspend fun addAmountToAlreadyExistingProduct(product: ProductModelClass) {
        cartCollectionReference.document(product.productName!!).update("productAmount",
            product.productAmount + (cartCollectionReference.document(product.productName).get().await().data?.getValue("productAmount") as Number).toInt())
    }

    suspend fun addNewProduct(product: ProductModelClass) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).set(product)
    }

    suspend fun getShopItems(): List<ProductModelClass> {
        return db.collection("Shop").get().await()
            .documents.mapNotNull {
                it.toObject(ProductModelClass::class.java)
            }
    }
}