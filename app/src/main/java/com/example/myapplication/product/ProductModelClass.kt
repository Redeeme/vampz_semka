package com.example.myapplication.product

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

    override fun hashCode(): Int {
        var result = productName?.hashCode() ?: 0
        result = 31 * result + (productOrigin?.hashCode() ?: 0)
        result = 31 * result + (productClass?.hashCode() ?: 0)
        result = 31 * result + productImage
        result = 31 * result + (productPrice?.hashCode() ?: 0)
        result = 31 * result + productAmount
        result = 31 * result + (productInfo?.hashCode() ?: 0)
        return result
    }
}