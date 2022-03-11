package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.ProductAdapter
import com.example.myapplication.Model.ProductModelClass
import com.example.myapplication.databinding.ActivityShopBinding
import com.google.firebase.auth.FirebaseAuth


class ShopActivity : AppCompatActivity(),IItemClickListener {

    private lateinit var binding: ActivityShopBinding
    private lateinit var itemAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        itemAdapter = ProductAdapter(this, loadData(), this@ShopActivity)
        binding.rvProductList.adapter = itemAdapter


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
    override fun minus(productModelClass: ProductModelClass, position: Int) {
        if (productModelClass.amount>0){
            productModelClass.amount--
            itemAdapter.notifyItemChanged(position)
        }
    }

    override fun addToCart(productModelClass: ProductModelClass, position: Int) {
        TODO("Not yet implemented")
    }

    override fun add(productModelClass: ProductModelClass, position: Int) {
        productModelClass.amount++
        itemAdapter.notifyItemChanged(position)
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