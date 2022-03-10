package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.CartItemAdapter
import com.example.myapplication.Model.CartItemModelClass
import com.example.myapplication.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        val itemAdapter = CartItemAdapter(this,loadData())
        binding.rvProductList.adapter = itemAdapter
    }

    private fun loadData(): ArrayList<CartItemModelClass> {
        val cartItemList: ArrayList<CartItemModelClass> = ArrayList()
        cartItemList.add(CartItemModelClass("Apples","Kazakhstan","f",R.drawable.f_apples,2.0,5))
        cartItemList.add(CartItemModelClass("Apricots","Kazakhstan","f",R.drawable.f_apricots,2.5,3))
        cartItemList.add(CartItemModelClass("Bananas","Kazakhstan","f",R.drawable.f_bananas,3.0,5))
        cartItemList.add(CartItemModelClass("Grapefruits","Kazakhstan","f",R.drawable.f_grapefruits,2.5,4))
        cartItemList.add(CartItemModelClass("Kiwis","Kazakhstan","f",R.drawable.f_kiwis,4.0,6))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        cartItemList.add(CartItemModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0,8))
        return cartItemList
    }
}