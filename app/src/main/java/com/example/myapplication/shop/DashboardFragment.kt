package com.example.myapplication.shop

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)


        //binding.tvUserEmail.text = "User ID : $emailId"
        //binding.tvUserId.text = "User ID : $userId"
        binding.tvUserEmail.text = "User ID : 123"
        binding.tvUserId.text = "User ID : 123"

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            //finish()

        }
        Handler().postDelayed({
            findNavController().navigate(R.id.action_dashboardFragment_to_mainFragment)
        }, 1000)
        return binding.root
    }
}