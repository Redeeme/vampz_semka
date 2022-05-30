package com.example.myapplication.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
    private var list: List<ProductModelClass> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        shopViewModel.data.observe(viewLifecycleOwner) {
            itemAdapter = ShopAdapter(this@ShopFragment)
            binding.rvProductList.layoutManager = LinearLayoutManager(context)
            binding.rvProductList.adapter = itemAdapter
            shopViewModel.setPrices()
            itemAdapter.setData(it)
            list=it
        }

        return binding.root
    }

    private fun filterList(newText: String?) {
        val filteredList = ArrayList<ProductModelClass>()
        for (product in list)
            if (newText != null) {
                if (product.productName?.lowercase()?.contains(newText.lowercase()) == true){
                    filteredList.add(product)
                }
            }
        if (filteredList.isNotEmpty()){
            itemAdapter.setData(filteredList)
        }
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