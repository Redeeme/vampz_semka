package com.example.myapplication.profile.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        val repository = CurrencyRepository()
        val currencyViewModelFactory = CurrencyViewModelFactory(repository)
        viewModel = ViewModelProvider(this,currencyViewModelFactory).get(CurrencyViewModel::class.java)
        viewModel.getRates()
        viewModel.ratesXResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response",response.base)
            Log.d("Response",response.date)
            Log.d("Response",response.rates.toString())
            Log.d("Response",response.success.toString())
            Log.d("Response",response.timestamp.toString())

        })


        return binding.root
    }
}