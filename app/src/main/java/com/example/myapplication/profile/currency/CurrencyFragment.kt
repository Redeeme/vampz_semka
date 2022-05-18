package com.example.myapplication.profile.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.CurrencyViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCurrencyBinding

class CurrencyFragment : Fragment(R.layout.fragment_currency) {

    lateinit var binding: FragmentCurrencyBinding

    private lateinit var viewModel :CurrencyViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)

        val currencyViewModelFactory = CurrencyViewModelFactory(activity?.application!!)

        viewModel = ViewModelProvider(this,currencyViewModelFactory)[CurrencyViewModel::class.java]

        binding.submitButton.setOnClickListener{
            Toast.makeText(
                context,
                binding.spToCurrency.selectedItem.toString(),
                Toast.LENGTH_SHORT
            ).show()
            binding.tvCurrent.text = binding.spToCurrency.selectedItem.toString()
            viewModel.readAllDataBase.observe(viewLifecycleOwner, Observer { local ->
                viewModel.setBase(local)
                viewModel.updateCurrency()
            })
        }


        return binding.root
    }
}