package com.example.data.network

import com.example.data.model.Category
import com.example.data.model.Order
import com.example.data.model.ShippingMethod
import retrofit2.Response
import retrofit2.http.*

interface OvisitoApiService {

    // 7.0 User Auth
    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginResponseData>>

    // 6.1 Products
    @GET("public/souvenir/products")
    suspend fun getProducts(
        @Query("search") search: String? = null,
        @Query("category") category: String? = null,
        @Query("featured") featured: Boolean? = null,
        @Query("sort") sort: String? = "latest",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): Response<ProductListResponse>

    @GET("public/souvenir/products/{slug}")
    suspend fun getProductDetail(
        @Path("slug") slug: String
    ): Response<ApiResponse<ProductDetailData>>

    @GET("public/souvenir/featured")
    suspend fun getFeaturedProducts(
        @Query("limit") limit: Int = 8
    ): Response<ApiResponse<List<com.example.data.model.Product>>>

    // 6.2 Categories
    @GET("public/souvenir/categories")
    suspend fun getCategories(
        @Query("with_children") withChildren: Boolean = true,
        @Query("only_parents") onlyParents: Boolean = true
    ): Response<ApiResponse<List<Category>>>

    @GET("public/souvenir/categories/{slug}")
    suspend fun getCategoryDetail(
        @Path("slug") slug: String,
        @Query("per_page") perPage: Int = 20
    ): Response<ApiResponse<CategoryDetailData>>

    // 6.4 Shipping Methods
    @GET("public/souvenir/shipping-methods")
    suspend fun getShippingMethods(): Response<ApiResponse<List<ShippingMethod>>>

    @POST("public/souvenir/shipping-methods/calculate")
    suspend fun calculateShipping(
        @Body request: ShippingCalculateRequest
    ): Response<ApiResponse<ShippingCalculateData>>

    // 7.1 Customer Orders
    @POST("customer/orders")
    suspend fun createOrder(
        @Body request: PostOrderRequest
    ): Response<ApiResponse<CreateOrderResponseData>>

    @GET("customer/orders")
    suspend fun getCustomerOrders(
        @Query("status") status: String? = null,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): Response<ApiResponse<List<Order>>>

    @GET("customer/orders/{order_number}")
    suspend fun getOrderDetail(
        @Path("order_number") orderNumber: String
    ): Response<ApiResponse<Order>>

    @POST("customer/orders/{order_number}/cancel")
    suspend fun cancelOrder(
        @Path("order_number") orderNumber: String,
        @Body reason: Map<String, String> = mapOf("reason" to "Berubah pikiran")
    ): Response<ApiResponse<Map<String, Any>>>

    @GET("customer/orders/{order_number}/track")
    suspend fun trackOrder(
        @Path("order_number") orderNumber: String
    ): Response<ApiResponse<Order>>

    // 7.2 Payment
    @GET("customer/payment/status/{bookingCode}")
    suspend fun getPaymentStatus(
        @Path("bookingCode") bookingCode: String
    ): Response<ApiResponse<PaymentStatusData>>
}
