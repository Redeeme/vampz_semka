package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.ProductAdapter
import com.example.myapplication.Model.ProductModelClass
import com.example.myapplication.databinding.ActivityShopBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class ShopActivity : AppCompatActivity(), IItemClickListener {

    private lateinit var binding: ActivityShopBinding
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        itemAdapter = ProductAdapter(this, loadData(), this@ShopActivity)
        binding.rvProductList.adapter = itemAdapter
        db = FirebaseFirestore.getInstance()

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ShopActivity, LoginActivity::class.java))
            finish()
        }
        binding.btnShop.setOnClickListener {
            startActivity(Intent(this@ShopActivity, ShopActivity::class.java))
            finish()
        }
        binding.btnShoppingCart.setOnClickListener {
            startActivity(Intent(this@ShopActivity, CartActivity::class.java))
            finish()
        }

    }

    override fun minus(product: ProductModelClass, position: Int) {
        if (product.productAmount > 0) {
            product.productAmount--
            itemAdapter.notifyItemChanged(position)
            Toast.makeText(
                    this@ShopActivity,
                    "Amount of ${product.productName} have been decreased.",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun addToCart(product: ProductModelClass, position: Int) {
        if (product.productAmount >= 1) {
            val docIdRef: DocumentReference = db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            docIdRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        Toast.makeText(
                                this@ShopActivity,
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
                                this@ShopActivity,
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
                    this@ShopActivity,
                    "You must choose atleast 1kg of ${product.productName} to add it.",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun add(product: ProductModelClass, position: Int) {
        product.productAmount++
        itemAdapter.notifyItemChanged(position)
        Toast.makeText(
                this@ShopActivity,
                "Amount of ${product.productName} has been increased.",
                Toast.LENGTH_SHORT
        ).show()
    }


    private fun loadData(): ArrayList<ProductModelClass> {
        val productList: ArrayList<ProductModelClass> = ArrayList()
        productList.add(ProductModelClass("Apples", "Kazakhstan", "f", R.drawable.f_apples, 2.0, 0))
        productList.add(ProductModelClass("Apricots", "Kazakhstan", "f", R.drawable.f_apricots, 2.5, 0))
        productList.add(ProductModelClass("Bananas", "Kazakhstan", "f", R.drawable.f_bananas, 3.0, 0))
        productList.add(ProductModelClass("Grapefruits", "Kazakhstan", "f", R.drawable.f_grapefruits, 2.5, 0))
        productList.add(ProductModelClass("Kiwis", "Kazakhstan", "f", R.drawable.f_kiwis, 4.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))
        productList.add(ProductModelClass("Limes", "Kazakhstan", "f", R.drawable.f_limes, 3.0, 0))

        return productList
    }

}