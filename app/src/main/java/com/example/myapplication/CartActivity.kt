package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartItemAdapter
import com.example.myapplication.Model.ProductModelClass
import com.example.myapplication.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class CartActivity : AppCompatActivity() {
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
        itemAdapter = CartItemAdapter(cartItemList)
        binding.rvProductList.adapter = itemAdapter
        loadData()

    }

    private fun loadData() {
        db= FirebaseFirestore.getInstance()
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore",error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        cartItemList.add(dc.document.toObject(ProductModelClass::class.java))
                    }
                }
                itemAdapter.notifyDataSetChanged()
            }

        })





        /*db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                .get().addOnSuccessListener{result->
                    var i:Int = 0

                    for(document in result){
                        Log.d(null,"${document.id}=>${document.data} + ${i}")
                        var pi: Long = document.data.getValue("productImage") as Long
                        var pp: Double = document.data.getValue("productPrice") as Double
                        var pa: Long = document.data.getValue("productAmount") as Long
                        cartItemList.add(ProductModelClass(
                                document.data.getValue("productName") as String,
                                document.data.getValue("productOrigin") as String,
                                document.data.getValue("productClass") as String,
                                pi.toInt(),
                                pp,
                                pa.toInt()
                        ))

                    }
                }
        cartItemList.add(ProductModelClass("Apples", "Kazakhstan", "f", R.drawable.f_apples, 2.0, 0))
        cartItemList.add(ProductModelClass("Apricots", "Kazakhstan", "f", R.drawable.f_apricots, 2.5, 0))
        cartItemList.add(ProductModelClass("Bananas", "Kazakhstan", "f", R.drawable.f_bananas, 3.0, 0))


        return cartItemList*/
    }
}
