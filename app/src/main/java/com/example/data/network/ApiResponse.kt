package com.example.data.network

import com.example.data.model.Category
import com.example.data.model.Order
import com.example.data.model.Product
import com.example.data.model.ShippingAddress
import com.example.data.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String? = null,
    @Json(name = "data") val data: T? = null
)

@JsonClass(generateAdapter = true)
data class ApiMeta(
    @Json(name = "current_page") val currentPage: Int = 1,
    @Json(name = "last_page") val lastPage: Int = 1,
    @Json(name = "per_page") val perPage: Int = 12,
    @Json(name = "total") val total: Int = 0
)

@JsonClass(generateAdapter = true)
data class ProductListResponse(
    @Json(name = "status") val status: Boolean,
    @Json(name = "data") val data: List<Product> = emptyList(),
    @Json(name = "meta") val meta: ApiMeta? = null
)

@JsonClass(generateAdapter = true)
data class ProductDetailData(
    @Json(name = "product") val product: Product,
    @Json(name = "related") val related: List<Product> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CategoryDetailData(
    @Json(name = "category") val category: Category,
    @Json(name = "products") val products: List<Product> = emptyList(),
    @Json(name = "meta") val meta: ApiMeta? = null
)

@JsonClass(generateAdapter = true)
data class ShippingCalculateRequest(
    @Json(name = "weight") val weight: Int,
    @Json(name = "method_id") val methodId: String
)

@JsonClass(generateAdapter = true)
data class ShippingCalculateData(
    @Json(name = "method") val method: String,
    @Json(name = "courier_code") val courierCode: String,
    @Json(name = "weight") val weight: Int,
    @Json(name = "cost") val cost: Double,
    @Json(name = "cost_formatted") val costFormatted: String
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "device_name") val deviceName: String = "android-customer-device"
)

@JsonClass(generateAdapter = true)
data class LoginResponseData(
    @Json(name = "token") val token: String,
    @Json(name = "user") val user: User
)

@JsonClass(generateAdapter = true)
data class PostOrderRequest(
    @Json(name = "merchant_uuid") val merchantUuid: String,
    @Json(name = "items") val items: List<PostOrderItem>,
    @Json(name = "shipping_address") val shippingAddress: ShippingAddress,
    @Json(name = "shipping_cost") val shippingCost: Long,
    @Json(name = "courier") val courier: String,
    @Json(name = "notes") val notes: String? = null
)

@JsonClass(generateAdapter = true)
data class PostOrderItem(
    @Json(name = "product_uuid") val productUuid: String,
    @Json(name = "quantity") val quantity: Int
)

@JsonClass(generateAdapter = true)
data class CreateOrderResponseData(
    @Json(name = "order") val order: Order,
    @Json(name = "payment") val payment: PaymentData? = null
)

@JsonClass(generateAdapter = true)
data class PaymentData(
    @Json(name = "next_step") val nextStep: String? = null,
    @Json(name = "booking_type") val bookingType: String? = null,
    @Json(name = "booking_id") val bookingId: String? = null,
    @Json(name = "payment_url") val paymentUrl: String? = null
)

@JsonClass(generateAdapter = true)
data class PaymentStatusData(
    @Json(name = "booking_code") val bookingCode: String,
    @Json(name = "is_paid") val isPaid: Boolean,
    @Json(name = "amount") val amount: Double,
    @Json(name = "status") val status: String,
    @Json(name = "booking_type") val bookingType: String
)
