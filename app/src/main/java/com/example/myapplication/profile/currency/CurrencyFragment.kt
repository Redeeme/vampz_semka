package com.example.myapplication.profile.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCurrencyBinding
import com.example.myapplication.profile.currency.currencyRequest.CurrencyRepository

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
        val currencyViewModelFactory = activity?.application?.let {
            CurrencyViewModelFactory(repository, it)
        }

        viewModel = (currencyViewModelFactory?.let {
            ViewModelProvider(this, it)
        }?.get(CurrencyViewModel::class.java) ?: viewModel.getRates()) as CurrencyViewModel

        viewModel.ratesXResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response",response.base)
            Log.d("Response",response.date)
            Log.d("Response",response.rates.toString())
            Log.d("Response",response.success.toString())
            Log.d("Response",response.timestamp.toString())
            viewModel.updateCurrency(response)
        })

        binding.submitButton.setOnClickListener{
            Toast.makeText(
                context,
                binding.spToCurrency.selectedItem.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }


        return binding.root
    }
}