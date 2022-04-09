package com.example.myapplication.shop

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShopBinding
import com.example.myapplication.product.ProductModelClass

class ShopFragment : Fragment(R.layout.fragment_shop), IProductClickListener {
//
    private lateinit var binding: FragmentShopBinding
    private lateinit var itemAdapter: ShopAdapter
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        shopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]

        shopViewModel.data.observe(viewLifecycleOwner) {
            itemAdapter = ShopAdapter(it, this@ShopFragment)
            binding.rvProductList.layoutManager = LinearLayoutManager(context)
            binding.rvProductList.adapter = itemAdapter
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun minus(product: ProductModelClass, position: Int) {
        shopViewModel.minus(product)
        itemAdapter.notifyDataSetChanged()
    }
    override fun add(product: ProductModelClass, position: Int) {
        shopViewModel.add(product)
        itemAdapter.notifyDataSetChanged()
    }

    override fun cartButton(product: ProductModelClass, position: Int) {
        shopViewModel.cartButton(
            product,
            requireContext()
        )
    }

    override fun show(product: ProductModelClass) {
        findNavController().navigate(
            R.id.action_shopFragment_to_productDetailFragment,
            shopViewModel.show(product)
        )
    }
}