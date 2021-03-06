package com.example.myapplication.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IProductClickListener
import com.example.myapplication.databinding.ItemCartLayoutBinding
import com.example.myapplication.shop.ProductModelClass
/*
adapter pri zobrazovani obshaju kosika
*/
class CartAdapter(private val itemClickListener: IProductClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<ProductModelClass> = ArrayList()

    class ViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductModelClass) {
            binding.productItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = ItemCartLayoutBinding.inflate(view, parent, false)

        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])

        holder.binding.sub.setOnClickListener {
            itemClickListener.minus(list[position], position)
        }
        holder.binding.add.setOnClickListener {
            itemClickListener.add(list[position], position)
        }
        holder.binding.ivDeleteFromCart.setOnClickListener {
            itemClickListener.cartButton(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: ArrayList<ProductModelClass>) {
        val diffUtil = CartDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }
}