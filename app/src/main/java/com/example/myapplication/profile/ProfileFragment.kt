package com.example.myapplication.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    lateinit var adapter: ProfileAdapter

    private val profileViewModel: ProfileViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        lifecycleScope.launch{
            withContext(Dispatchers.Main){
                profileViewModel.update()
            }
        }

        profileViewModel._profileData.observe(viewLifecycleOwner) {
            adapter = ProfileAdapter(it)
            binding.rvOrderList.layoutManager = LinearLayoutManager(context)
            binding.rvOrderList.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        binding.goToCurrency.setOnClickListener{
            goToCurrency()
        }
        binding.goToBackground.setOnClickListener{
            Toast.makeText(
                context,
                "uwu",
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

    private fun goToCurrency(){
        findNavController().navigate(
            R.id.action_profileFragment_to_currencyFragment
        )
    }
}