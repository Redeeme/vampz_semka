package com.example.myapplication.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    lateinit var adapter: ProfileAdapter

    private lateinit var profileViewModel: ProfileViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        profileViewModel._profileData.observe(viewLifecycleOwner) {
            adapter = ProfileAdapter(it)
            binding.rvOrderList.layoutManager = LinearLayoutManager(context)
            binding.rvOrderList.adapter = adapter
        }

        return binding.root
    }
}