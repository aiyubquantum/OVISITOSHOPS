package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShippingMethod(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "courier_code") val courierCode: String,
    @Json(name = "description") val description: String,
    @Json(name = "base_cost") val baseCost: String,
    @Json(name = "is_active") val isActive: Boolean = true
) {
    val costAmount: Long
        get() = baseCost.toDoubleOrNull()?.toLong() ?: 25000L

    fun getFormattedCost(): String {
        return "Rp %,d".format(costAmount).replace(',', '.')
    }
}
