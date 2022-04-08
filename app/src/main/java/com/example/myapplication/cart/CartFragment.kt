package com.example.myapplication.cart

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.OrderModelClass
import com.example.myapplication.product.ProductModelClass
import com.example.myapplication.productDetail.ProductDetailFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CartFragment : Fragment(R.layout.fragment_cart), IProductClickListener {
    lateinit var binding: FragmentCartBinding
    lateinit var itemAdapter: CartAdapter
    lateinit var cartItemList: ArrayList<ProductModelClass>
    lateinit var rvProductList: RecyclerView
    val cartViewModel: CartFragmentViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.viewModel = cartViewModel


        binding.rvProductList.layoutManager = LinearLayoutManager(context)
        binding.rvProductList.setHasFixedSize(true)
        cartItemList = ArrayList()
        loadData()

        itemAdapter = CartAdapter(cartItemList, this@CartFragment)
        binding.rvProductList.adapter = itemAdapter
        binding.btnCheckout.setOnClickListener {
            checkout()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkout() {
        var price = 0.0
        var items = 0
        for (product in cartItemList){

         price += (product.productPrice!! * product.productAmount)
         items += product.productAmount
         }
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = current.format(formatter)

        var order = OrderModelClass(0,FirebaseAuth.getInstance().currentUser!!.uid,formatted,price.toString(),items.toString())
        addOrder(order)
        removeFromDb()

    }

    private fun addOrder(order: OrderModelClass) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid + "+")
            .add(order)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeFromDb() {
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
            context,
            "Order has been sent.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun minus(product: ProductModelClass, position: Int) {
        cartViewModel.minus(product,posiz)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun cartButton(product: ProductModelClass, position: Int) {
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            .delete()
            .addOnSuccessListener { Log.d("firestore", "DocumentSnapshot successfully deleted!") }
            .addOnSuccessListener { Log.w("firestore", "Error deleting document") }
        cartItemList.removeAt(position)
        itemAdapter.notifyDataSetChanged()
        setData()
    }

    override fun show(product: ProductModelClass) {
        val bundle = bundleOf("product" to product)
        val fragment = ProductDetailFragment()
        fragment.arguments = bundle
    }


    override fun add(product: ProductModelClass, position: Int) {
        product.productAmount++
        itemAdapter.notifyItemChanged(position)
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!).update("productAmount", product.productAmount)
    }


}