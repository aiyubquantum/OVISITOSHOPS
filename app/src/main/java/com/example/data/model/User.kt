package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "uuid") val uuid: String = "bbb6859f-7de1-4d35-899c-eef4cf6b6732",
    @Json(name = "name") val name: String = "Muhammad Aiyub",
    @Json(name = "email") val email: String = "aiyub@ovisito.com",
    @Json(name = "phone") val phone: String = "+62 812 3456 3113",
    @Json(name = "status") val status: String = "active",
    val memberSince: String = "2025",
    val address: ShippingAddress = ShippingAddress(
        name = "Muhammad Aiyub",
        phone = "6281234563113",
        address = "Jl. Teuku Umar No. 12",
        city = "Banda Aceh",
        postalCode = "23116"
    )
)

@JsonClass(generateAdapter = true)
data class ShippingAddress(
    @Json(name = "name") val name: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "address") val address: String,
    @Json(name = "city") val city: String? = "Banda Aceh",
    @Json(name = "postal_code") val postalCode: String? = "23116"
) {
    val fullFormattedAddress: String
        get() = "$address, $city, Aceh $postalCode"
}
