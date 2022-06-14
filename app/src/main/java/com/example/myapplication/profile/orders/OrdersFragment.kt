package com.example.myapplication.profile.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrdersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
/*
prepojenie adaptera a viewmodela objednavok
*/
@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    lateinit var binding: FragmentOrdersBinding

    lateinit var adapter: OrderAdapter

    private val ordersViewModel: OrdersViewModel by viewModels<OrdersViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        lifecycleScope.launch{
            withContext(Dispatchers.Main){
                ordersViewModel.update()
            }
        }

        ordersViewModel._profileData.observe(viewLifecycleOwner) {
            adapter = OrderAdapter(it)
            binding.rvOrderList.layoutManager = LinearLayoutManager(context)
            binding.rvOrderList.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}