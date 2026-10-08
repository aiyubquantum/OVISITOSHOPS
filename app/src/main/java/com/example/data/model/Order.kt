package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderItem(
    @Json(name = "id") val id: String,
    @Json(name = "product_uuid") val productUuid: String,
    @Json(name = "product_name") val productName: String,
    @Json(name = "price") val price: String,
    @Json(name = "formatted_price") val formattedPrice: String? = null,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "subtotal") val subtotal: String,
    @Json(name = "formatted_subtotal") val formattedSubtotal: String? = null,
    @Json(name = "product") val product: Product? = null
)

@JsonClass(generateAdapter = true)
data class OrderTracking(
    @Json(name = "id") val id: String,
    @Json(name = "status") val status: String,
    @Json(name = "description") val description: String,
    @Json(name = "location") val location: String,
    @Json(name = "tracked_at") val trackedAt: String
)

@JsonClass(generateAdapter = true)
data class Order(
    @Json(name = "id") val id: String,
    @Json(name = "order_number") val orderNumber: String,
    @Json(name = "status") val status: String, // pending, paid, processing, shipped, completed, cancelled
    @Json(name = "status_label") val statusLabel: String,
    @Json(name = "payment_status") val paymentStatus: String,
    @Json(name = "payment_status_label") val paymentStatusLabel: String? = null,
    @Json(name = "total_amount") val totalAmount: String,
    @Json(name = "formatted_total") val formattedTotal: String,
    @Json(name = "shipping_cost") val shippingCost: String? = "25000.00",
    @Json(name = "courier") val courier: String? = "jne",
    @Json(name = "tracking_number") val trackingNumber: String? = null,
    @Json(name = "items") val items: List<OrderItem> = emptyList(),
    @Json(name = "shipping_address") val shippingAddress: ShippingAddress? = null,
    @Json(name = "payment_url") val paymentUrl: String? = null,
    @Json(name = "flip_bill_id") val flipBillId: String? = null,
    @Json(name = "notes") val notes: String? = null,
    @Json(name = "ordered_at") val orderedAt: String? = null,
    @Json(name = "estimated_delivery") val estimatedDelivery: String? = null,
    @Json(name = "history") val history: List<OrderTracking> = emptyList()
)

data class CreateOrderRequest(
    val merchantUuid: String,
    val items: List<CreateOrderItemRequest>,
    val shippingAddress: ShippingAddress,
    val shippingCost: Long,
    val courier: String,
    val paymentMethod: String,
    val notes: String? = null
)

data class CreateOrderItemRequest(
    val productUuid: String,
    val quantity: Int
)

data class PaymentResult(
    val bookingCode: String,
    val amount: Long,
    val paymentUrl: String?,
    val qrCode: String?,
    val flipBillId: String?,
    val paymentMethod: String
)
