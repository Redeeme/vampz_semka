package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.tvUserId.text = FirebaseAuth.getInstance().currentUser.toString()

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

        }
        Handler().postDelayed({
            findNavController().navigate(
                R.id.action_dashboardFragment_to_shopFragment,    null,
                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                }
            )

        }, 2000)
        return binding.root
    }
}