package com.example.myapplication.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentProductDetailBinding
import com.example.myapplication.model.ProductModelClass

class ProductDetailFragment : Fragment() {
    lateinit var binding: FragmentProductDetailBinding
    lateinit var product: ProductModelClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        product = ProductModelClass(
            requireArguments().getString("productName"),
            requireArguments().getString("productOrigin"),
            requireArguments().getString("productClass"),
            requireArguments().getInt("productImage"),
            requireArguments().getDouble("productPrice"),
            requireArguments().getInt("productAmount")
        )
        return binding.root
    }
}