package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@DashboardActivity,LoginActivity::class.java))
            finish()
        }
        binding.btnShop.setOnClickListener{
            Toast.makeText(
                this@DashboardActivity,
                "Redirected to shop :)",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnAbout.setOnClickListener{
            Toast.makeText(
                this@DashboardActivity,
                "Redirected to About page :)",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnShoppingCart.setOnClickListener{
            Toast.makeText(
                this@DashboardActivity,
                "Redirected to shopping cart :)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}