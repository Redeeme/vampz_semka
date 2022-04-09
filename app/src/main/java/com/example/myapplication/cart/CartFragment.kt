package com.example.myapplication.cart

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.product.ProductModelClass
import com.example.myapplication.productDetail.ProductDetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CartFragment : Fragment(R.layout.fragment_cart), IProductClickListener {
    private lateinit var binding: FragmentCartBinding

    private lateinit var itemAdapter: CartAdapter

    private lateinit var cartViewModel: CartViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        lifecycleScope.launch{
            withContext(Dispatchers.Main){
                cartViewModel.update()
            }
        }

        cartViewModel._cartData.observe(viewLifecycleOwner) {
            itemAdapter = CartAdapter(this@CartFragment)
            binding.rvProductList.layoutManager = LinearLayoutManager(context)
            binding.rvProductList.adapter = itemAdapter
            itemAdapter.setData(it)
        }

        cartViewModel._orderPrice.observe(viewLifecycleOwner){
            binding.tvMoneySum.text = "$it kg"
        }

        cartViewModel._orderWeight.observe(viewLifecycleOwner){
            binding.tvWeightSum.text = "$it kg"
        }

        binding.btnCheckout.setOnClickListener {
            checkout()
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkout() {
        cartViewModel.checkout()
        Toast.makeText(
            context,
            "Order has been sent.",
            Toast.LENGTH_SHORT
        ).show()
        itemAdapter.notifyDataSetChanged()
    }

    override fun minus(product: ProductModelClass, position: Int) {
        cartViewModel.minus(product)
        itemAdapter.notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun cartButton(product: ProductModelClass, position: Int) {
        cartViewModel.cartButton(product,position)
        itemAdapter.setData(cartViewModel._cartData.value!!)
    }

    override fun show(product: ProductModelClass) {
        val fragment = ProductDetailFragment()
        fragment.arguments = cartViewModel.show(product)
    }

    override fun add(product: ProductModelClass, position: Int) {
        cartViewModel.add(product)
        itemAdapter.notifyItemChanged(position)
    }

}