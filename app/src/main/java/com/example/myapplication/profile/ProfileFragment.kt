package com.example.myapplication.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding

/*
sluzi len na navigaciu do bud volenia meny alebo zobrazenie objednavok
*/
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.toOrders.setOnClickListener{
            findNavController().navigate(
                R.id.action_profileFragment_to_ordersFragment
            )
        }
        binding.toCurrency.setOnClickListener{
            findNavController().navigate(
                R.id.action_profileFragment_to_currencyFragment
            )
        }

        return binding.root
    }
}