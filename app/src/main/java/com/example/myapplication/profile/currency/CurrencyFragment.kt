package com.example.myapplication.profile.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint
/*
zobrazovanie fragmentu mien a moznost volby
*/

@AndroidEntryPoint
class CurrencyFragment : Fragment(R.layout.fragment_currency) {

    lateinit var binding: FragmentCurrencyBinding

    private val viewModel: CurrencyViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)


        viewModel._currentCurr.observe(viewLifecycleOwner){
            binding.tvCurrent.text = it
        }

        binding.submitButton.setOnClickListener{
            Toast.makeText(
                context,
                binding.spToCurrency.selectedItem.toString(),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.convert(binding.spToCurrency.selectedItem.toString())
        }


        return binding.root
    }
}