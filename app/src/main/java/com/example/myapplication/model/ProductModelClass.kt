package com.example.myapplication.model

data class ProductModelClass(
    val productName: String? = "",
    val productOrigin: String? = "",
    val productClass: String? = "",
    val productImage: Int = 0,
    val productPrice: Double? = 0.0,
    var productAmount: Int = 0,
    val productInfo: String? = ""
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as ProductModelClass

        if (productName != other.productName) {
            return false
        }
        if (productOrigin != other.productOrigin) {
            return false
        }
        if (productClass != other.productClass) {
            return false
        }
        if (productImage != other.productImage) {
            return false
        }
        if (productPrice != other.productPrice) {
            return false
        }
        if (productAmount != other.productAmount) {
            return false
        }
        if (productInfo != other.productInfo) {
            return false
        }
        return true
    }
}