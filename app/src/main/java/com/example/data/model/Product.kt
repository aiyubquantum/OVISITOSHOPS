package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id") val id: String,
    @Json(name = "merchant_uuid") val merchantUuid: String? = null,
    @Json(name = "store_id") val storeId: String? = null,
    @Json(name = "category_id") val categoryId: String? = null,
    @Json(name = "name") val name: String,
    @Json(name = "slug") val slug: String,
    @Json(name = "sku") val sku: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "price") val price: String,
    @Json(name = "discount_price") val discountPrice: String? = null,
    @Json(name = "final_price") val finalPrice: Double,
    @Json(name = "stock") val stock: Int = 10,
    @Json(name = "weight") val weight: String? = "0.25",
    @Json(name = "images") val images: List<String> = emptyList(),
    @Json(name = "is_active") val isActive: Boolean = true,
    @Json(name = "featured") val featured: Boolean = false,
    @Json(name = "views") val views: Int = 0,
    @Json(name = "category") val category: Category? = null,
    @Json(name = "merchant") val merchant: Merchant? = null,
    @Json(name = "store") val store: Store? = null,
    @Json(name = "rating") val rating: Double = 4.8,
    @Json(name = "review_count") val reviewCount: Int = 120,
    @Json(name = "is_best_seller") val isBestSeller: Boolean = false,
    @Json(name = "is_local_aceh") val isLocalAceh: Boolean = true,
    @Json(name = "is_halal") val isHalal: Boolean = true,
    val localDrawableRes: Int? = null
) {
    fun getFormattedPrice(): String {
        val amount = finalPrice.toLong()
        return "Rp %,d".format(amount).replace(',', '.')
    }

    fun getFormattedOriginalPrice(): String? {
        val orig = price.toDoubleOrNull() ?: return null
        if (orig > finalPrice) {
            return "Rp %,d".format(orig.toLong()).replace(',', '.')
        }
        return null
    }

    fun getImageUrl(): String? {
        val first = images.firstOrNull() ?: return null
        return if (first.startsWith("http://") || first.startsWith("https://")) {
            first
        } else {
            "https://api.ovisito.com/storage/$first"
        }
    }
}

@JsonClass(generateAdapter = true)
data class Merchant(
    @Json(name = "uuid") val uuid: String,
    @Json(name = "name") val name: String,
    @Json(name = "business_name") val businessName: String,
    @Json(name = "verified_status") val verifiedStatus: String? = "verified"
)

@JsonClass(generateAdapter = true)
data class Store(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "slug") val slug: String,
    @Json(name = "logo") val logo: String? = null,
    @Json(name = "address") val address: String? = null,
    @Json(name = "phone") val phone: String? = null
)

@JsonClass(generateAdapter = true)
data class Review(
    @Json(name = "id") val id: String,
    @Json(name = "rating") val rating: Int,
    @Json(name = "review") val review: String,
    @Json(name = "is_verified_purchase") val isVerifiedPurchase: Boolean = true,
    @Json(name = "user_name") val userName: String = "Pelanggan",
    @Json(name = "created_at") val createdAt: String? = null
)
