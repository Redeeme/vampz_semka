package com.example.myapplication.cart

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.shop.ProductModelClass
/*
sluzi na zmenu itemov v adaptery
*/

class CartDiffUtil(
    private val oldList: ArrayList<ProductModelClass>,
    private val newList: ArrayList<ProductModelClass>
): DiffUtil.Callback() {
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