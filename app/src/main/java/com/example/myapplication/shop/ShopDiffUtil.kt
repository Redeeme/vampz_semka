package com.example.myapplication.shop

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.product.ProductModelClass

class ShopDiffUtil(
    private val oldList: List<ProductModelClass>,
    private val newList: List<ProductModelClass>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].productImage == newList[newItemPosition].productImage
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition] != newList[newItemPosition] -> false
            else -> true

        }
    }
}