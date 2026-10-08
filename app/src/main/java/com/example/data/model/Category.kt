package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "slug") val slug: String,
    @Json(name = "description") val description: String? = null,
    @Json(name = "parent_id") val parentId: String? = null,
    @Json(name = "is_active") val isActive: Boolean = true,
    @Json(name = "product_count") val productCount: Int = 0,
    @Json(name = "icon_name") val iconName: String? = null,
    @Json(name = "children") val children: List<Category> = emptyList()
)

data class CategoryDisplayItem(
    val id: String,
    val name: String,
    val slug: String,
    val productCount: Int,
    val iconKey: String,
    val isPrimary: Boolean = false
)
