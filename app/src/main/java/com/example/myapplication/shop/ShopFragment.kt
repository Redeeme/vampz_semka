package com.example.myapplication.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShopBinding
import com.example.myapplication.model.ProductModelClass

class ShopFragment : Fragment(R.layout.fragment_shop), IProductClickListener {
    private lateinit var binding: FragmentShopBinding
    private lateinit var itemAdapter: ShopAdapter
    private lateinit var viewModel: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            itemAdapter = ShopAdapter(it!!,this@ShopFragment)
            binding.rvProductList.layoutManager = LinearLayoutManager(context)
            binding.rvProductList.adapter = itemAdapter
        })

        return binding.root
    }

    override fun minus(product: ProductModelClass, position: Int) {
        viewModel.minus(product)
        itemAdapter.notifyDataSetChanged()
    }
    override fun add(product: ProductModelClass, position: Int) {
        viewModel.add(product)
        itemAdapter.notifyDataSetChanged()
    }

    override fun cartButton(product: ProductModelClass, position: Int) {
        viewModel.cartButton(product,requireContext())
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
}