package com.example.myapplication.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShopBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopFragment : Fragment(R.layout.fragment_shop), IProductClickListener {
//
    private lateinit var binding: FragmentShopBinding
    private lateinit var itemAdapter: ShopAdapter
    private val shopViewModel by viewModels<ShopViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        shopViewModel.data.observe(viewLifecycleOwner) {
            itemAdapter = ShopAdapter(this@ShopFragment)
            binding.rvProductList.layoutManager = LinearLayoutManager(context)
            binding.rvProductList.adapter = itemAdapter
            shopViewModel.setPrices()
            itemAdapter.setData(it)
        }

        return binding.root
    }

    override fun minus(product: ProductModelClass, position: Int) {
        shopViewModel.minus(product,position)
        itemAdapter.notifyItemChanged(position)
    }
    override fun add(product: ProductModelClass, position: Int) {
        shopViewModel.add(product,position)
        itemAdapter.notifyItemChanged(position)
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