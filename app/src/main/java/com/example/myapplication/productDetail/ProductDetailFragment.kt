package com.example.myapplication.productDetail

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentProductDetailBinding
import com.example.myapplication.product.ProductModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var product: ProductModelClass


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
            requireArguments().getInt("productAmount"),
            requireArguments().getString("productInfo")
        )
        binding.idProductImage.setImageResource(requireArguments().getInt("productImage"))
        binding.idProductName.text = requireArguments().getString("productName")
        binding.idProductPrice.text = requireArguments().getDouble("productPrice").toString()+ " €/kg"
        binding.idProductOrigin.text = "Origin: " + requireArguments().getString("productOrigin")
        binding.productInfo.text = requireArguments().getString("productInfo")


        binding.ivAddToCart.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.etProductAmount.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter amount.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    product.productAmount = binding.etProductAmount.text.toString().toInt()
                    cartButton(product)
                    view?.hideSoftInput()
                }
            }
        }
        binding.tvAddToCart.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.etProductAmount.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter amount.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    product.productAmount = binding.etProductAmount.text.toString().toInt()
                    cartButton(product)
                    view?.hideSoftInput()
                }
            }
        }

        return binding.root
    }

    private fun cartButton(product: ProductModelClass) {
        if (product.productAmount >= 1) {
            val db = FirebaseFirestore.getInstance()
            val docIdRef: DocumentReference = db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName!!)
            docIdRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        Toast.makeText(
                            context,
                            "product is already in cart :)",
                            Toast.LENGTH_SHORT
                        ).show()
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    if (document.data.getValue("productName") == product.productName) {
                                        val i: Int = ((document.data.getValue("productAmount")) as Number).toInt() + product.productAmount
                                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(
                                            product.productName
                                        ).update("productAmount", i)
                                    }
                                }

                            }
                    } else {
                        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document(product.productName).set(product)
                        Toast.makeText(
                            context,
                            "${product.productName} have been added to cart.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.d("firestore", "Failed with: ", task.exception)
                }
            }
        } else {
            Toast.makeText(
                context,
                "You must choose atleast 1kg of ${product.productName} to add it.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun View.hideSoftInput() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}