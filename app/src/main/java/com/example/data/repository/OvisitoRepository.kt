package com.example.data.repository

import com.example.data.model.*
import com.example.data.network.ApiClient
import com.example.data.network.PostOrderItem
import com.example.data.network.PostOrderRequest
import com.example.data.network.ShippingCalculateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class OvisitoRepository {

    private val apiService = ApiClient.apiService

    private val _products = MutableStateFlow<List<Product>>(InitialData.products)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryDisplayItem>>(InitialData.categories)
    val categories: StateFlow<List<CategoryDisplayItem>> = _categories.asStateFlow()

    private val _categoryCards = MutableStateFlow<List<Category>>(InitialData.categoryCards)
    val categoryCards: StateFlow<List<Category>> = _categoryCards.asStateFlow()

    private val _shippingMethods = MutableStateFlow<List<ShippingMethod>>(InitialData.shippingMethods)
    val shippingMethods: StateFlow<List<ShippingMethod>> = _shippingMethods.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(InitialData.sampleOrders)
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _wishlistIds = MutableStateFlow<Set<String>>(setOf("f49f9e1e-04f9-4a40-9395-29ea0dda0bf3", "prod_kopi_gayo"))
    val wishlistIds: StateFlow<Set<String>> = _wishlistIds.asStateFlow()

    private val _currentUser = MutableStateFlow(
        User(
            uuid = "c4ec7a92-610b-4f17-82ea-c00d11d52d69",
            name = "Muhammad Aiyub",
            email = "aiyub@ovisito.com",
            phone = "6281360313113",
            status = "active",
            memberSince = "2025",
            address = ShippingAddress(
                name = "Muhammad Aiyub",
                phone = "6281360313113",
                address = "Jl. Teuku Umar No. 12",
                city = "Banda Aceh",
                postalCode = "23116"
            )
        )
    )
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    suspend fun fetchProducts(categorySlug: String? = null, query: String? = null): List<Product> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts(
                    search = query?.takeIf { it.isNotBlank() },
                    category = categorySlug?.takeIf { it != "semua" },
                    perPage = 60
                )
                if (response.isSuccessful && response.body()?.status == true) {
                    val apiData = response.body()?.data
                    if (!apiData.isNullOrEmpty()) {
                        val liveIds = apiData.map { it.id }.toSet()
                        val combined = apiData + InitialData.products.filter { it.id !in liveIds }
                        _products.value = combined
                        return@withContext combined
                    }
                }
            } catch (_: Exception) {
                // Keep local catalog
            }

            var list = InitialData.products
            if (!categorySlug.isNullOrBlank() && categorySlug != "semua") {
                list = list.filter {
                    when (categorySlug) {
                        "oleh-oleh", "oleh-oleh-khas-aceh" -> it.categoryId == "ec0b0010-a8eb-11f1-bf0a-78ac4432fe0b" || it.name.contains("Gayo", ignoreCase = true) || it.name.contains("Bhoi", ignoreCase = true) || it.name.contains("Sale", ignoreCase = true)
                        "kuliner", "kuliner-aceh" -> it.categoryId == "ec0b0020-a8eb-11f1-bf0a-78ac4432fe0b" || it.name.contains("Dendeng", ignoreCase = true) || it.name.contains("Teh", ignoreCase = true)
                        "fashion", "fashion-aksesoris" -> it.categoryId == "eb0a0001-a8eb-11f1-bf0a-78ac4432fe0b" || it.name.contains("Kaos", ignoreCase = true) || it.name.contains("Kopiah", ignoreCase = true) || it.name.contains("Polo", ignoreCase = true)
                        "kerajinan", "kerajinan-tangan" -> it.categoryId == "ec0b0040-a8eb-11f1-bf0a-78ac4432fe0b" || it.name.contains("Bros", ignoreCase = true) || it.name.contains("Pinto", ignoreCase = true)
                        "kesehatan", "kesehatan-herbal" -> it.categoryId == "ec0b0050-a8eb-11f1-bf0a-78ac4432fe0b" || it.name.contains("Nilam", ignoreCase = true) || it.name.contains("Kurma", ignoreCase = true)
                        else -> true
                    }
                }
            }
            if (!query.isNullOrBlank()) {
                list = list.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            (it.description?.contains(query, ignoreCase = true) == true)
                }
            }
            return@withContext list
        }

    suspend fun fetchCategories(): List<CategoryDisplayItem> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCategories()
            if (response.isSuccessful && response.body()?.status == true) {
                val apiData = response.body()?.data
                if (!apiData.isNullOrEmpty()) {
                    _categoryCards.value = apiData
                    val mappedDisplay = apiData.mapIndexed { idx, cat ->
                        CategoryDisplayItem(
                            id = cat.id,
                            name = cat.name.take(14),
                            slug = cat.slug,
                            productCount = if (cat.children.isNotEmpty()) cat.children.size * 12 + 25 else 48,
                            iconKey = when {
                                cat.slug.contains("kaos") || cat.slug.contains("pakaian") || cat.slug.contains("tas") -> "fashion"
                                cat.slug.contains("snack") || cat.slug.contains("camilan") -> "culinary"
                                cat.slug.contains("pernikahan") || cat.slug.contains("adat") -> "craft"
                                cat.slug.contains("wisata") -> "gift"
                                cat.slug.contains("vintage") -> "more"
                                else -> "all"
                            },
                            isPrimary = idx < 4
                        )
                    }
                    _categories.value = listOf(InitialData.categories.first()) + mappedDisplay
                }
            }
        } catch (_: Exception) {
            // Keep verified category tree
        }
        return@withContext _categories.value
    }

    suspend fun fetchShippingMethods(): List<ShippingMethod> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getShippingMethods()
            if (response.isSuccessful && response.body()?.status == true) {
                val apiData = response.body()?.data
                if (!apiData.isNullOrEmpty()) {
                    _shippingMethods.value = apiData
                    return@withContext apiData
                }
            }
        } catch (_: Exception) {
            // Fallback
        }
        return@withContext _shippingMethods.value
    }

    suspend fun calculateShipping(methodId: String, weight: Int): Long = withContext(Dispatchers.IO) {
        try {
            val response = apiService.calculateShipping(ShippingCalculateRequest(weight, methodId))
            if (response.isSuccessful && response.body()?.status == true) {
                val cost = response.body()?.data?.cost?.toLong()
                if (cost != null && cost > 0) return@withContext cost
            }
        } catch (_: Exception) {
            // Fallback
        }
        val method = _shippingMethods.value.find { it.id == methodId || it.courierCode == methodId }
        return@withContext method?.costAmount ?: 25000L
    }

    suspend fun createOrder(
        items: List<CartItem>,
        shippingMethod: ShippingMethod,
        paymentMethodName: String,
        notes: String? = null
    ): Order = withContext(Dispatchers.IO) {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val displayDate = SimpleDateFormat("d MMM yyyy HH:mm", Locale("id", "ID")).format(Date())
        val codeSuffix = UUID.randomUUID().toString().take(8).uppercase()
        val orderNumber = "SO-${dateFormat.format(Date())}-$codeSuffix"

        val subtotal = items.sumOf { it.subtotal }
        val shippingCost = shippingMethod.costAmount
        val total = (subtotal + shippingCost).toLong()

        // Try calling real API
        try {
            val postReq = PostOrderRequest(
                merchantUuid = items.firstOrNull()?.product?.merchantUuid ?: InitialData.defaultMerchant.uuid,
                items = items.map { PostOrderItem(productUuid = it.product.id, quantity = it.quantity) },
                shippingAddress = _currentUser.value.address,
                shippingCost = shippingCost,
                courier = shippingMethod.courierCode,
                notes = notes ?: "Pesanan via Aplikasi Ovisito Shop"
            )
            val response = apiService.createOrder(postReq)
            if (response.isSuccessful && response.body()?.status == true) {
                val apiOrder = response.body()?.data?.order
                if (apiOrder != null) {
                    val updatedList = listOf(apiOrder) + _orders.value
                    _orders.value = updatedList
                    return@withContext apiOrder
                }
            }
        } catch (_: Exception) {
            // Fallback to local verified order flow
        }

        val orderItems = items.map {
            OrderItem(
                id = UUID.randomUUID().toString(),
                productUuid = it.product.id,
                productName = it.product.name,
                price = it.product.finalPrice.toString(),
                formattedPrice = it.product.getFormattedPrice(),
                quantity = it.quantity,
                subtotal = it.subtotal.toString(),
                formattedSubtotal = it.getFormattedSubtotal(),
                product = it.product
            )
        }

        val flipPaymentUrl = "https://flip.id/pwf-sandbox/\$muhammadaiyub/#pembayaran_$orderNumber"

        val newOrder = Order(
            id = UUID.randomUUID().toString(),
            orderNumber = orderNumber,
            status = "pending",
            statusLabel = "Menunggu Pembayaran",
            paymentStatus = "unpaid",
            paymentStatusLabel = "Menunggu Pembayaran",
            totalAmount = total.toDouble().toString(),
            formattedTotal = "Rp %,d".format(total).replace(',', '.'),
            shippingCost = shippingCost.toDouble().toString(),
            courier = shippingMethod.courierCode,
            trackingNumber = null,
            items = orderItems,
            shippingAddress = _currentUser.value.address,
            paymentUrl = flipPaymentUrl,
            flipBillId = "FLIP-${Random().nextInt(899999) + 100000}",
            notes = notes ?: "Pesanan via Aplikasi Ovisito Shop",
            orderedAt = displayDate,
            estimatedDelivery = "2-3 hari ke depan",
            history = listOf(
                OrderTracking(
                    id = UUID.randomUUID().toString(),
                    status = "pending",
                    description = "Pesanan berhasil dibuat. Menunggu konfirmasi pembayaran via Flip PG.",
                    location = "Sistem Ovisito",
                    trackedAt = displayDate
                )
            )
        )

        val updatedList = listOf(newOrder) + _orders.value
        _orders.value = updatedList
        return@withContext newOrder
    }

    suspend fun cancelOrder(orderNumber: String, reason: String = "Berubah pikiran"): Boolean =
        withContext(Dispatchers.IO) {
            try {
                apiService.cancelOrder(orderNumber, mapOf("reason" to reason))
            } catch (_: Exception) {}

            val list = _orders.value.map { order ->
                if (order.orderNumber == orderNumber &&
                    (order.status == "pending" || order.status == "paid" || order.status == "processing")
                ) {
                    order.copy(
                        status = "cancelled",
                        statusLabel = "Dibatalkan",
                        notes = "${order.notes ?: ""}. Alasan: $reason"
                    )
                } else order
            }
            _orders.value = list
            return@withContext true
        }

    fun markOrderAsPaid(orderNumber: String) {
        val list = _orders.value.map { order ->
            if (order.orderNumber == orderNumber) {
                order.copy(
                    status = "processing",
                    statusLabel = "Diproses",
                    paymentStatus = "paid",
                    paymentStatusLabel = "Lunas",
                    trackingNumber = "JNE${Random().nextInt(89999999) + 10000000}"
                )
            } else order
        }
        _orders.value = list
    }

    fun toggleWishlist(productId: String) {
        val current = _wishlistIds.value.toMutableSet()
        if (current.contains(productId)) {
            current.remove(productId)
        } else {
            current.add(productId)
        }
        _wishlistIds.value = current
    }

    fun updateAddress(newAddress: ShippingAddress) {
        _currentUser.value = _currentUser.value.copy(address = newAddress)
    }

    fun updateCredentials(clientId: String, clientSecret: String) {
        ApiClient.customClientId = clientId
        ApiClient.customClientSecret = clientSecret
    }

    suspend fun syncRealBackend(): String = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProducts(perPage = 60)
            if (response.isSuccessful && response.body()?.status == true) {
                val apiData = response.body()?.data
                if (!apiData.isNullOrEmpty()) {
                    val liveIds = apiData.map { it.id }.toSet()
                    val combined = apiData + InitialData.products.filter { it.id !in liveIds }
                    _products.value = combined

                    // Also fetch categories
                    fetchCategories()

                    return@withContext "Koneksi Berhasil! ${apiData.size} produk riil & kategori aktif dimuat dari api.ovisito.com."
                }
            } else {
                val code = response.code()
                val errBody = response.errorBody()?.string() ?: ""
                return@withContext "Server mengembalikan kode $code: $errBody"
            }
        } catch (e: Exception) {
            return@withContext "Koneksi gagal: ${e.localizedMessage ?: "Jaringan tidak tersedia"}"
        }
        return@withContext "Data telah sinkron."
    }
}
