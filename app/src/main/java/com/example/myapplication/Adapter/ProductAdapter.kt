package com.example.myapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.ProductModelClass
import com.example.myapplication.R

class ProductAdapter(private val context: Context, val list: ArrayList<ProductModelClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_product_layout, parent, false)

        return ViewHolder(view,mListener)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        (holder as ViewHolder).tvProductName.text = itemsViewModel.productName
        holder.tvProductOrigin.text = itemsViewModel.productOrigin
        holder.ivProductImage.setImageResource(itemsViewModel.productImage)
        holder.tvProductPrice.text = itemsViewModel.productPrice.toString() + " €/kg"
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(ItemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.id_productName)
        val tvProductOrigin: TextView = itemView.findViewById(R.id.id_productOrigin)
        val ivProductImage: ImageView = itemView.findViewById(R.id.id_productImage)
        val tvProductPrice: TextView = itemView.findViewById(R.id.id_productPrice)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}