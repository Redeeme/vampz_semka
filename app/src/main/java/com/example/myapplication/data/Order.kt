package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myapplication.model.ProductModelClass
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: String,
    val date: String
   // val purchasedItems: List<ProductModelClass>
)




class Converters {

        @TypeConverter
        fun fromString(value: String?): ArrayList<ProductModelClass> {
            val list = object : TypeToken<ArrayList<ProductModelClass?>?>(){}.type
            return Gson().fromJson(value, list)
        }

        @TypeConverter
        fun fromArrayList(list: ArrayList<ProductModelClass?>?): String {
            val gson = Gson()
            return gson.toJson(list)
        }


}