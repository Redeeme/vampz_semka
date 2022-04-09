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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.ProductModelClass
import com.example.myapplication.productDetail.ProductDetailFragment


class CartFragment : Fragment(R.layout.fragment_cart), IProductClickListener {
    lateinit var binding: FragmentCartBinding

    lateinit var itemAdapter: CartAdapter

    private lateinit var viewModel: CartViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        viewModel.data.observe(viewLifecycleOwner) {
            itemAdapter = CartAdapter(it, this@CartFragment)
            binding.rvProductList.layoutManager = LinearLayoutManager(context)
            binding.rvProductList.adapter = itemAdapter
        }

        viewModel.orderPrice.observe(viewLifecycleOwner){
            binding.tvMoneySum.text = viewModel.orderPrice.value.toString() +" kg"
        }

        viewModel.orderWeight.observe(viewLifecycleOwner){
            binding.tvWeightSum.text = viewModel.orderWeight.value.toString() +" kg"
        }

        binding.btnCheckout.setOnClickListener {
            checkout()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkout() {
        viewModel.checkout()
        Toast.makeText(
            context,
            "Order has been sent.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun minus(product: ProductModelClass, position: Int) {
        viewModel.minus(product)
        itemAdapter.notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun cartButton(product: ProductModelClass, position: Int) {
        viewModel.cartButton(product,position)
        itemAdapter.notifyDataSetChanged()
    }

    override fun show(product: ProductModelClass) {
        val fragment = ProductDetailFragment()
        fragment.arguments = viewModel.show(product)
    }


    override fun add(product: ProductModelClass, position: Int) {
        viewModel.add(product)
        itemAdapter.notifyItemChanged(position)
    }


}