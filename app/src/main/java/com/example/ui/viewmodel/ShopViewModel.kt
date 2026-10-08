package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.InitialData
import com.example.data.repository.OvisitoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Screen {
    object Onboarding : Screen()
    object Home : Screen()
    object Categories : Screen()
    data class CategoryProducts(val categorySlug: String, val title: String) : Screen()
    data class ProductDetail(val product: Product) : Screen()
    object Cart : Screen()
    object Checkout : Screen()
    data class OrderSuccess(val order: Order) : Screen()
    object Orders : Screen()
    data class OrderDetail(val order: Order) : Screen()
    object Profile : Screen()
    object Wishlist : Screen()
}

enum class BottomTab {
    HOME, CATEGORIES, CART, ORDERS, PROFILE
}

class ShopViewModel(
    private val repository: OvisitoRepository = OvisitoRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _currentTab = MutableStateFlow(BottomTab.HOME)
    val currentTab: StateFlow<BottomTab> = _currentTab.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryDisplayItem>>(emptyList())
    val categories: StateFlow<List<CategoryDisplayItem>> = _categories.asStateFlow()

    private val _categoryCards = repository.categoryCards
    val categoryCards: StateFlow<List<Category>> = _categoryCards

    private val _selectedCategorySlug = MutableStateFlow("semua")
    val selectedCategorySlug: StateFlow<String> = _selectedCategorySlug.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Cart matching mockup: 3 items totaling Rp 205.000
    private val _cartItems = MutableStateFlow<List<CartItem>>(
        listOf(
            CartItem(InitialData.products[0], quantity = 1, isSelected = true), // Kopi Gayo 85k
            CartItem(InitialData.products[1], quantity = 1, isSelected = true), // Kue Bhoi 45k
            CartItem(InitialData.products[2], quantity = 1, isSelected = true)  // Sarikurma 75k
        )
    )
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val shippingMethods = repository.shippingMethods
    private val _selectedShippingMethod = MutableStateFlow(InitialData.shippingMethods[0])
    val selectedShippingMethod: StateFlow<ShippingMethod> = _selectedShippingMethod.asStateFlow()

    private val _selectedPaymentMethod = MutableStateFlow("Transfer Bank (BCA, BRI, BNI, Mandiri)")
    val selectedPaymentMethod: StateFlow<String> = _selectedPaymentMethod.asStateFlow()

    val orders = repository.orders
    val wishlistIds = repository.wishlistIds
    val currentUser = repository.currentUser

    // Orders Filter Tab
    private val _selectedOrderFilter = MutableStateFlow("Semua")
    val selectedOrderFilter: StateFlow<String> = _selectedOrderFilter.asStateFlow()

    // Flip / QRIS payment modal active order
    private val _activePaymentOrder = MutableStateFlow<Order?>(null)
    val activePaymentOrder: StateFlow<Order?> = _activePaymentOrder.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _categories.value = repository.fetchCategories()
            _products.value = repository.fetchProducts(_selectedCategorySlug.value, _searchQuery.value)
            _isLoading.value = false
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
        when (screen) {
            is Screen.Home -> _currentTab.value = BottomTab.HOME
            is Screen.Categories, is Screen.CategoryProducts -> _currentTab.value = BottomTab.CATEGORIES
            is Screen.Cart -> _currentTab.value = BottomTab.CART
            is Screen.Orders, is Screen.OrderDetail -> _currentTab.value = BottomTab.ORDERS
            is Screen.Profile -> _currentTab.value = BottomTab.PROFILE
            else -> {}
        }
    }

    fun selectTab(tab: BottomTab) {
        _currentTab.value = tab
        when (tab) {
            BottomTab.HOME -> _currentScreen.value = Screen.Home
            BottomTab.CATEGORIES -> _currentScreen.value = Screen.Categories
            BottomTab.CART -> _currentScreen.value = Screen.Cart
            BottomTab.ORDERS -> _currentScreen.value = Screen.Orders
            BottomTab.PROFILE -> _currentScreen.value = Screen.Profile
        }
    }

    fun selectCategory(slug: String) {
        _selectedCategorySlug.value = slug
        viewModelScope.launch {
            _products.value = repository.fetchProducts(slug, _searchQuery.value)
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _products.value = repository.fetchProducts(_selectedCategorySlug.value, query)
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val existing = current[index]
            current[index] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            current.add(CartItem(product, quantity = quantity, isSelected = true))
        }
        _cartItems.value = current
        showMessage("Ditambahkan ke keranjang")
    }

    fun updateCartQuantity(productId: String, delta: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == productId }
        if (index >= 0) {
            val item = current[index]
            val newQty = item.quantity + delta
            if (newQty <= 0) {
                current.removeAt(index)
            } else {
                current[index] = item.copy(quantity = newQty)
            }
            _cartItems.value = current
        }
    }

    fun removeCartItem(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun toggleCartItemSelection(productId: String) {
        _cartItems.value = _cartItems.value.map {
            if (it.product.id == productId) it.copy(isSelected = !it.isSelected) else it
        }
    }

    fun selectShippingMethod(method: ShippingMethod) {
        _selectedShippingMethod.value = method
    }

    fun selectPaymentMethod(methodName: String) {
        _selectedPaymentMethod.value = methodName
    }

    fun selectOrderFilter(filter: String) {
        _selectedOrderFilter.value = filter
    }

    fun checkout() {
        viewModelScope.launch {
            val selectedItems = _cartItems.value.filter { it.isSelected }
            if (selectedItems.isEmpty()) {
                showMessage("Pilih setidaknya 1 produk untuk checkout")
                return@launch
            }
            val newOrder = repository.createOrder(
                items = selectedItems,
                shippingMethod = _selectedShippingMethod.value,
                paymentMethodName = _selectedPaymentMethod.value
            )
            // Remove checked out items from cart
            _cartItems.value = _cartItems.value.filter { !it.isSelected }
            _currentScreen.value = Screen.OrderSuccess(newOrder)
        }
    }

    fun payNow(order: Order) {
        _activePaymentOrder.value = order
    }

    fun closePaymentModal() {
        _activePaymentOrder.value = null
    }

    fun confirmPaymentDone(orderNumber: String) {
        repository.markOrderAsPaid(orderNumber)
        _activePaymentOrder.value = null
        showMessage("Pembayaran berhasil dikonfirmasi!")
    }

    fun cancelOrder(orderNumber: String, reason: String = "Berubah pikiran") {
        viewModelScope.launch {
            val success = repository.cancelOrder(orderNumber, reason)
            if (success) {
                showMessage("Pesanan $orderNumber berhasil dibatalkan")
            }
        }
    }

    fun toggleWishlist(productId: String) {
        repository.toggleWishlist(productId)
    }

    fun showMessage(msg: String) {
        _userMessage.value = msg
    }

    fun clearMessage() {
        _userMessage.value = null
    }

    fun updateAddress(address: ShippingAddress) {
        repository.updateAddress(address)
        showMessage("Alamat berhasil diperbarui")
    }

    fun syncBackend() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.syncRealBackend()
            _isLoading.value = false
            showMessage(result)
        }
    }

    fun updateCredentials(clientId: String, clientSecret: String) {
        repository.updateCredentials(clientId, clientSecret)
        syncBackend()
    }
}
