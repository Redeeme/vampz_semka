
package com.example.myapplication.profile.background

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBackgroundBinding

class BackgroundFragment : Fragment(R.layout.fragment_background) {

    lateinit var binding: FragmentBackgroundBinding


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBackgroundBinding.inflate(inflater, container, false)

        return binding.root
    }
}