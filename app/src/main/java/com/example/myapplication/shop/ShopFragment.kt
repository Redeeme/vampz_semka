package com.example.myapplication.shop

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.adapter.ShopAdapter
import com.example.myapplication.databinding.FragmentShopBinding
import com.example.myapplication.product.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopFragment : Fragment(R.layout.fragment_shop), IProductClickListener {
    private lateinit var binding: FragmentShopBinding
    private lateinit var itemAdapter: ShopAdapter
    private lateinit var db: FirebaseFirestore
    lateinit var shopItemList: ArrayList<ProductModelClass>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        binding.rvProductList.layoutManager = LinearLayoutManager(context)
        db = FirebaseFirestore.getInstance()
        shopItemList = ArrayList()
        lifecycleScope.launch {
            loadData()
        }
        itemAdapter = ShopAdapter(shopItemList,this@ShopFragment)
        binding.rvProductList.adapter = itemAdapter

        return binding.root
    }



    override fun minus(product: ProductModelClass, position: Int) {
        if (product.productAmount > 0) {
            product.productAmount--
            itemAdapter.notifyItemChanged(position)
        }
    }

    override fun cartButton(product: ProductModelClass, position: Int) {
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

    override fun show(product: ProductModelClass) {
        val bundle = bundleOf("productName" to product.productName,
            "productOrigin" to product.productOrigin,
            "productClass" to product.productClass,
            "productImage" to product.productImage,
            "productPrice" to product.productPrice,
            "productAmount" to product.productAmount,
            "productInfo" to product.productInfo)

        findNavController().navigate(R.id.action_shopFragment_to_productDetailFragment,bundle)
    }


    override fun add(product: ProductModelClass, position: Int) {
        product.productAmount++
        itemAdapter.notifyItemChanged(position)
    }


    private suspend fun loadData() {
        withContext(Dispatchers.IO) {
            db.collection("Shop").addSnapshotListener(object :
                EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            shopItemList.add(dc.document.toObject(ProductModelClass::class.java))
                        }
                    }
                    shopItemList.sortedBy { product -> product.productClass }
                    itemAdapter.notifyDataSetChanged()
                }

            })
        }
    }
}