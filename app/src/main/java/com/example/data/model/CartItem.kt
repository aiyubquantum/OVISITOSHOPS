package com.example.data.model

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val isSelected: Boolean = true
) {
    val subtotal: Double
        get() = product.finalPrice * quantity

    fun getFormattedSubtotal(): String {
        return "Rp %,d".format(subtotal.toLong()).replace(',', '.')
    }
}
