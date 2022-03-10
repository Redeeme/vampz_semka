package com.example.myapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.CartItemModelClass
import com.example.myapplication.R

class CartItemAdapter (private val context: Context, val list: ArrayList<CartItemModelClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.cart_item_layout, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        (holder as ViewHolder).tvProductName.text = itemsViewModel.CartItemName
        holder.tvProductOrigin.text = itemsViewModel.CartItemOrigin
        holder.ivProductImage.setImageResource(itemsViewModel.CartItemImage)
        holder.tvProductPrice.text = itemsViewModel.CartItemPrice.toString() + " €/kg"
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.id_productName)
        val tvProductOrigin: TextView = itemView.findViewById(R.id.id_productOrigin)
        val ivProductImage: ImageView = itemView.findViewById(R.id.id_productImage)
        val tvProductPrice: TextView = itemView.findViewById(R.id.id_productPrice)

    }
}