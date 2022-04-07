package com.example.myapplication.shop

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IProductClickListener
import com.example.myapplication.R
import com.example.myapplication.product.ProductModelClass

class ShopAdapter(val itemClickListener: IProductClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: ArrayList<ProductModelClass> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_layout, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        (holder as ViewHolder).tvProductName.text = itemsViewModel.productName
        holder.tvProductOrigin.text = itemsViewModel.productOrigin
        holder.ivProductImage.setImageResource(itemsViewModel.productImage)
        holder.tvProductPrice.text = itemsViewModel.productPrice.toString() + " €/kg"
        holder.tvCount.text = "${itemsViewModel.productAmount}"
        holder.ivSub.setOnClickListener{
            itemClickListener.minus(list[position],position)
        }
        holder.ivAdd.setOnClickListener{
            itemClickListener.add(list[position],position)
        }
        holder.ivAddToCart.setOnClickListener{
            itemClickListener.cartButton(list[position],position)
        }
        holder.tvAddtoCart.setOnClickListener {
            itemClickListener.cartButton(list[position],position)
        }
        holder.ivProductImage.setOnClickListener {
            itemClickListener.show(list[position])
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.id_productName)
        val tvProductOrigin: TextView = itemView.findViewById(R.id.id_productOrigin)
        val ivProductImage: ImageView = itemView.findViewById(R.id.id_productImage)
        val tvProductPrice: TextView = itemView.findViewById(R.id.id_productPrice)
        val tvCount: TextView = itemView.findViewById(R.id.id_productAmount)
        val ivAdd: ImageView = itemView.findViewById(R.id.add)
        val ivSub: ImageView = itemView.findViewById(R.id.sub)
        val ivAddToCart: ImageView = itemView.findViewById(R.id.iv_add_to_cart)
        val tvAddtoCart: TextView = itemView.findViewById(R.id.tv_add_to_cart)
    }
    fun submit(newList: ArrayList<ProductModelClass>){
        val oldList = list
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ProductItemDiffCallback(
                oldList,
                newList
            )
        )
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }
    class ProductItemDiffCallback(
        var oldProductList: List<ProductModelClass>,
        var newProductList: List<ProductModelClass>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldProductList.size
        }

        override fun getNewListSize(): Int {
            return newProductList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldProductList[oldItemPosition].productImage == newProductList[newItemPosition].productImage)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldProductList[oldItemPosition] == newProductList[newItemPosition]
        }

    }
}