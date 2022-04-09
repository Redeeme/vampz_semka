package com.example.myapplication.shop

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IProductClickListener
import com.example.myapplication.databinding.ItemProductLayoutBinding
import com.example.myapplication.product.ProductModelClass

class ShopAdapter(private val itemClickListener: IProductClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ProductModelClass> = emptyList()
    class ViewHolder(val binding: ItemProductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModelClass){
            binding.productItem = item
        }

        /*val tvProductName: TextView = itemView.findViewById(R.id.id_productName)
        val tvProductOrigin: TextView = itemView.findViewById(R.id.id_productOrigin)
        val ivProductImage: ImageView = itemView.findViewById(R.id.id_productImage)
        val tvProductPrice: TextView = itemView.findViewById(R.id.id_productPrice)
        val tvCount: TextView = itemView.findViewById(R.id.id_productAmount)
        val ivAdd: ImageView = itemView.findViewById(R.id.add)
        val ivSub: ImageView = itemView.findViewById(R.id.sub)
        val ivAddToCart: ImageView = itemView.findViewById(R.id.iv_add_to_cart)
        val tvAddToCart: TextView = itemView.findViewById(R.id.tv_add_to_cart)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = ItemProductLayoutBinding.inflate(view,parent,false)

        return ViewHolder(listItemBinding)
    }

    //TODO data bindings
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])

        /*val itemsViewModel = list[position]
        (holder as ViewHolder).tvProductName.text = itemsViewModel.productName
        holder.tvProductOrigin.text = itemsViewModel.productOrigin
        holder.ivProductImage.setImageResource(itemsViewModel.productImage)
        holder.tvProductPrice.text = itemsViewModel.productPrice.toString() + " €/kg"
        holder.tvCount.text = "${itemsViewModel.productAmount}"
        */
        holder.binding.sub.setOnClickListener{
            itemClickListener.minus(list[position],position)
        }
        holder.binding.add.setOnClickListener{
            itemClickListener.add(list[position],position)
        }
        holder.binding.idProductImage.setOnClickListener{
            itemClickListener.show(list[position])
        }
        holder.binding.tvAddToCart.setOnClickListener {
            itemClickListener.cartButton(list[position],position)
        }
        /*holder.ivProductImage.setOnClickListener {
            itemClickListener.show(list[position])
        }*/
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