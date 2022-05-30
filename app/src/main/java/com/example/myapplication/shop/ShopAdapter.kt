package com.example.myapplication.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IProductClickListener
import com.example.myapplication.databinding.ItemProductLayoutBinding

class ShopAdapter(private val itemClickListener: IProductClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<ProductModelClass> = emptyList()

    class ViewHolder(val binding: ItemProductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductModelClass){
            binding.productItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = ItemProductLayoutBinding.inflate(view,parent,false)

        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])

        holder.binding.sub.setOnClickListener{

            if (itemCount == 1 && position > 0){
                itemClickListener.minus(list[0],0)
            }else{
                itemClickListener.minus(list[position],position)
            }
        }
        holder.binding.add.setOnClickListener{
            if (itemCount == 1 && position > 0){
                itemClickListener.add(list[0],0)
            }else{
                itemClickListener.add(list[position],position)
            }
        }
        holder.binding.idProductImage.setOnClickListener{

            if (itemCount == 1 && position > 0){
                itemClickListener.show(list[0])
            }else{
                itemClickListener.show(list[position])
            }
        }
        holder.binding.tvAddToCart.setOnClickListener {

            if (itemCount == 1 && position > 0){
                itemClickListener.cartButton(list[0],0)
            }else{
                itemClickListener.cartButton(list[position],position)
            }
        }

    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: List<ProductModelClass>){
        val diffUtil = ShopDiffUtil(list,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }
}