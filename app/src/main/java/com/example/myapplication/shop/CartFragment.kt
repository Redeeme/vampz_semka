package com.example.myapplication.shop

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.CartAdapter
import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderViewModel
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CartFragment : Fragment(R.layout.fragment_cart), IProductClickListener {
    lateinit var binding: FragmentCartBinding
    private lateinit var db: FirebaseFirestore
    lateinit var itemAdapter: CartAdapter
    lateinit var cartItemList: ArrayList<ProductModelClass>
    lateinit var rvProductList: RecyclerView
    lateinit var mOrderViewModel: OrderViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.rvProductList.layoutManager = LinearLayoutManager(context)
        binding.rvProductList.setHasFixedSize(true)
        cartItemList = ArrayList()
        itemAdapter = CartAdapter(cartItemList, this@CartFragment)
        mOrderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        binding.rvProductList.adapter = itemAdapter
        loadData()
        binding.btnCheckout.setOnClickListener {
            checkout()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkout() {

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = current.format(formatter)
        var price = 0.0
        var items = 0
        for (order in cartItemList){
            price += order.productPrice!!
            items++
        }
        var order = Order(0,FirebaseAuth.getInstance().currentUser!!.uid,formatted,price,items)//,cartItemList)
        mOrderViewModel.addOrder(order)
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
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).addSnapshotListener(object :
            EventListener<QuerySnapshot> {
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