package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartItemAdapter
import com.example.myapplication.Model.ProductModelClass
import com.example.myapplication.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class CartActivity : AppCompatActivity(), IItemClickListener {
    lateinit var binding: ActivityCartBinding
    private lateinit var db: FirebaseFirestore
    lateinit var itemAdapter: CartItemAdapter
    lateinit var cartItemList: ArrayList<ProductModelClass>
    lateinit var rvProductList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        binding.rvProductList.setHasFixedSize(true)
        cartItemList = ArrayList()
        itemAdapter = CartItemAdapter(cartItemList, this@CartActivity)
        binding.rvProductList.adapter = itemAdapter
        loadData()
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@CartActivity, LoginActivity::class.java))
            finish()
        }
        binding.btnShop.setOnClickListener {
            startActivity(Intent(this@CartActivity, ShopActivity::class.java))
            finish()
        }
        binding.btnShoppingCart.setOnClickListener {
            startActivity(Intent(this@CartActivity, CartActivity::class.java))
            finish()
        }
        binding.btnCheckout.setOnClickListener {
            checkout()
        }

    }

    private fun checkout() {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var key: String = (document.data.getValue("productName").toString())
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(key).delete()

                    }
                }
        cartItemList.clear()
        itemAdapter.notifyDataSetChanged()
        setData()
        Toast.makeText(
                this@CartActivity,
                "Order has been sent.",
                Toast.LENGTH_SHORT
        ).show()
    }

    private fun setData() {
        var money: Double = 0.0
        var weight: Int = 0
        for (item in cartItemList) {
            money += (item.productAmount * item.productPrice!!)
            weight += item.productAmount
        }
        binding.tvMoneySum.text = money.toString() + " €"
        binding.tvWeightSum.text = weight.toString() +" kg"

    }

    private fun loadData() {
        db = FirebaseFirestore.getInstance()
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                itemAdapter.notifyDataSetChanged()
                setData()
            }

        })
    }

    override fun minus(product: ProductModelClass, position: Int) {
        if (product.productAmount > 0) {
            product.productAmount--
            itemAdapter.notifyItemChanged(position)
            db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", product.productAmount)
        }
    }

    override fun cartButton(product: ProductModelClass, position: Int) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
                .delete()
                .addOnSuccessListener { Log.d("firestore", "DocumentSnapshot successfully deleted!") }
                .addOnSuccessListener { Log.w("firestore", "Error deleting document") }
        cartItemList.removeAt(position)
        itemAdapter.notifyDataSetChanged()
        setData()
    }

    override fun add(product: ProductModelClass, position: Int) {
        product.productAmount++
        itemAdapter.notifyItemChanged(position)
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", product.productAmount)
    }
}
