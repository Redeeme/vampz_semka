package com.example.myapplication.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.OrderModelClass

class ProfileAdapter (val list: ArrayList<OrderModelClass>) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_layout, parent, false)

        return ViewHolder(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemsViewModel = list[position]

        (holder as ViewHolder).tvOrderDate.text = itemsViewModel.date
        holder.tvOrderPrice.text = itemsViewModel.price + " €/kg"
        holder.tvOrderAmount.text = itemsViewModel.items + "items"
        holder.tvOrderId.text = itemsViewModel.id.toString()
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvOrderDate: TextView = itemView.findViewById(R.id.date)
        var tvOrderPrice: TextView = itemView.findViewById(R.id.price)
        val tvOrderAmount: TextView = itemView.findViewById(R.id.item_count)
        val tvOrderId: TextView = itemView.findViewById(R.id.id)

    }
}