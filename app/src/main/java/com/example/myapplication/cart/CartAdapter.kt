package com.example.myapplication.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IProductClickListener
import com.example.myapplication.databinding.ItemCartLayoutBinding
import com.example.myapplication.product.ProductModelClass

class CartAdapter (val list: ArrayList<ProductModelClass>, val itemClickListener: IProductClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : CartItemViewHolder {
        return CartItemViewHolder.from(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        (holder as CartItemViewHolder).tvProductName.text = itemsViewModel.productName
        holder.tvProductOrigin.text = itemsViewModel.productOrigin
        holder.ivProductImage.setImageResource(itemsViewModel.productImage)
        holder.tvProductPrice.text = itemsViewModel.productPrice.toString() + " €/kg"
        holder.tvProductAmount.text = itemsViewModel.productAmount.toString()
        holder.ivSub.setOnClickListener{
            itemClickListener.minus(list[position],position)
        }
        holder.ivAdd.setOnClickListener{
            itemClickListener.add(list[position],position)
        }
        holder.ivDeleteFromCart.setOnClickListener{
            itemClickListener.cartButton(list[position],position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class CartItemViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CartItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCartLayoutBinding.inflate(layoutInflater,
                    parent, false)
                return CartItemViewHolder(binding)
            }
        }

        val tvProductName: TextView = binding.idProductName
        val tvProductOrigin: TextView = binding.idProductOrigin
        val ivProductImage: ImageView = binding.idProductImage
        val tvProductPrice: TextView = binding.idProductPrice
        val tvProductAmount: TextView = binding.idProductAmount
        val ivAdd: ImageView = binding.add
        val ivSub: ImageView = binding.sub
        val ivDeleteFromCart: ImageView = binding.ivDeleteFromCart

    }

}