package com.example.myapplication.profile.currency.currencyLocal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.profile.currency.models.Rates
import com.fasterxml.jackson.databind.ObjectMapper



/*
Model tabulky vktora sa da Room databazy
*/
@Entity(tableName = "base_table")
data class CurrencyLocalModel(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    @PrimaryKey
    val date: String,
    @TypeConverters(MapTypeConverter::class)
    val currencyMap: Map<String,Double>
)
/*
Type convertory pre ukladanie Objektov do Room databazy
*/
class MapTypeConverter{
    @TypeConverter
    fun JsontoMap(json: String): MutableMap<String, Double>? {
        val mapper = ObjectMapper()
        val map = mapper.readValue(json, MutableMap::class.java) as MutableMap<String, Double>?
        return map

    }

    @TypeConverter
    fun MapToJson(map: Map<*,*>): String{
        val mapper = ObjectMapper()
        var json: String = mapper.writeValueAsString(map)
        println(json) // compact-print
        json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)
        return json
    }
    fun RatestoMap(rates: Rates): MutableMap<String, Double>? {
        val mapper = ObjectMapper()
        val map = mapper.convertValue(rates, MutableMap::class.java) as MutableMap<String, Double>?
        return map

    }
    fun MapToRates(map: Map<*,*>): Rates {
        val mapper = ObjectMapper()
        val rates = mapper.convertValue(MutableMap::class.java, Rates::class.java)
        return rates
    }
}

