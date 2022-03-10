package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Model.ProductModelClass
import com.example.myapplication.databinding.ActivityShopBinding
import com.google.firebase.auth.FirebaseAuth


class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        val itemAdapter = ProductAdapter(this,loadData())
        binding.rvProductList.adapter = itemAdapter
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ShopActivity,LoginActivity::class.java))
            finish()
        }
        binding.btnShop.setOnClickListener{
            startActivity(Intent(this@ShopActivity,ShopActivity::class.java))
            finish()
        }
        binding.btnAddToCart.setOnClickListener{
            Toast.makeText(
                    this@ShopActivity,
                    "Redirected to About page :)",
                    Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnShoppingCart.setOnClickListener{
            Toast.makeText(
                    this@ShopActivity,
                    "Redirected to shopping cart :)",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun loadData(): ArrayList<ProductModelClass>{
        val productList: ArrayList<ProductModelClass> = ArrayList()
        productList.add(ProductModelClass("Apples","Kazakhstan","f",R.drawable.f_apples,2.0))
        productList.add(ProductModelClass("Apricots","Kazakhstan","f",R.drawable.f_apricots,2.5))
        productList.add(ProductModelClass("Bananas","Kazakhstan","f",R.drawable.f_bananas,3.0))
        productList.add(ProductModelClass("Grapefruits","Kazakhstan","f",R.drawable.f_grapefruits,2.5))
        productList.add(ProductModelClass("Kiwis","Kazakhstan","f",R.drawable.f_kiwis,4.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        productList.add(ProductModelClass("Limes","Kazakhstan","f",R.drawable.f_limes,3.0))
        return productList
    }

}