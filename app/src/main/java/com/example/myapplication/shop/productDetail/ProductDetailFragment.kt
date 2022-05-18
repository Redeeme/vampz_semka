package com.example.myapplication.shop.productDetail

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentProductDetailBinding
import com.example.myapplication.shop.ProductModelClass

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding

    private lateinit var productDetailViewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        productDetailViewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]

        productDetailViewModel.setProduct(
            ProductModelClass(
            requireArguments().getString("productName"),
            requireArguments().getString("productOrigin"),
            requireArguments().getString("productClass"),
            requireArguments().getInt("productImage"),
            requireArguments().getDouble("productPrice"),
            requireArguments().getInt("productAmount"),
            requireArguments().getString("productInfo")
        )
        )

        productDetailViewModel._productData.observe(viewLifecycleOwner) {
            binding.idProductImage.setImageResource(it.productImage)
            binding.idProductName.text = it.productName
            binding.idProductPrice.text = it.productPrice.toString()
            binding.idProductOrigin.text = it.productOrigin
            binding.productInfo.text = it.productInfo
        }

        binding.addToCart.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.etProductAmount.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(
                        context,
                        "Please enter amount.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    productDetailViewModel.setAmount(binding.etProductAmount.text.toString().toInt())
                    productDetailViewModel.cartButton(requireContext())
                    view?.hideSoftInput()
                }
            }
        }
        binding.tvAddToCart.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.etProductAmount.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(
                        context,
                        "Please enter amount.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    productDetailViewModel.setAmount(binding.etProductAmount.text.toString().toInt())
                    productDetailViewModel.cartButton(requireContext())
                    view?.hideSoftInput()
                }
            }
        }
        return binding.root
    }

    private fun View.hideSoftInput() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
