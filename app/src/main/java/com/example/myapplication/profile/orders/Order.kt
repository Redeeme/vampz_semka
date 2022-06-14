package com.example.myapplication.profile.orders
/*
sluzi ako model objednavky uzivatela
*/
data class OrderModelClass(
    var userId: String = "",
    var date: String = "",
    var price: String = "",
    var currency: String = "",
    var items: String = ""
)
