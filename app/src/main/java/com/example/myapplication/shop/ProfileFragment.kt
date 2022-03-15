package com.example.myapplication.shop

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ProfileAdapter
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.model.OrderModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    lateinit var adapter: ProfileAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var orderItemList: ArrayList<OrderModelClass>
    lateinit var rvOrderList: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.rvOrderList.layoutManager = LinearLayoutManager(context)
        binding.rvOrderList.setHasFixedSize(true)
        orderItemList = ArrayList()
        adapter = ProfileAdapter(orderItemList)

        db = FirebaseFirestore.getInstance()
        db.collection(FirebaseAuth.getInstance().currentUser!!.uid + "+")
            .get()
            .addOnSuccessListener { result ->
                var i: Int = 1
                for (document in result) {
                    val userId: String = document.data.get("userId") as String
                    val price: String = document.data.get("price") as String
                    val size: String = document.data.get("items") as String
                    val date: String = document.data.get("date") as String

                    orderItemList.add(
                        OrderModelClass(
                            i,
                            userId,
                            date,
                            price,
                            size
                        )
                    )
                    i++
                }
                adapter.notifyDataSetChanged()

            }
        binding.rvOrderList.adapter = adapter

        return binding.root
    }

}