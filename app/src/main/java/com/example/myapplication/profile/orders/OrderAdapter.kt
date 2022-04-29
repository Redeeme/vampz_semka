package com.example.myapplication.profile.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemOrderLayoutBinding
import com.example.myapplication.model.OrderModelClass

class OrderAdapter (private val list: ArrayList<OrderModelClass>) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(val binding: ItemOrderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderModelClass){
            binding.orderItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = ItemOrderLayoutBinding.inflate(view,parent,false)

        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}