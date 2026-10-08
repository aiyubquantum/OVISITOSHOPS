package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BottomNavBar
import com.example.ui.components.OvisitoTopBar
import com.example.ui.components.PaymentModal
import com.example.ui.screens.*
import com.example.ui.theme.OvisitoShopTheme
import com.example.ui.theme.OvisitoTeal
import com.example.ui.viewmodel.BottomTab
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.ShopViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OvisitoShopTheme {
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
                val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
                val products by viewModel.products.collectAsStateWithLifecycle()
                val categories by viewModel.categories.collectAsStateWithLifecycle()
                val categoryCards by viewModel.categoryCards.collectAsStateWithLifecycle()
                val selectedCategorySlug by viewModel.selectedCategorySlug.collectAsStateWithLifecycle()
                val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
                val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
                val shippingMethods by viewModel.shippingMethods.collectAsStateWithLifecycle()
                val selectedShippingMethod by viewModel.selectedShippingMethod.collectAsStateWithLifecycle()
                val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsStateWithLifecycle()
                val orders by viewModel.orders.collectAsStateWithLifecycle()
                val selectedOrderFilter by viewModel.selectedOrderFilter.collectAsStateWithLifecycle()
                val activePaymentOrder by viewModel.activePaymentOrder.collectAsStateWithLifecycle()
                val wishlistIds by viewModel.wishlistIds.collectAsStateWithLifecycle()
                val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
                val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(userMessage) {
                    userMessage?.let { msg ->
                        snackbarHostState.showSnackbar(msg)
                        viewModel.clearMessage()
                    }
                }

                // Payment Bottom Sheet
                activePaymentOrder?.let { order ->
                    PaymentModal(
                        order = order,
                        onDismiss = { viewModel.closePaymentModal() },
                        onConfirmPaid = { orderNumber ->
                            viewModel.confirmPaymentDone(orderNumber)
                        }
                    )
                }

                // Determine whether to show bottom navigation bar
                val isRootScreen = currentScreen is Screen.Home ||
                        currentScreen is Screen.Categories ||
                        currentScreen is Screen.Cart ||
                        currentScreen is Screen.Orders ||
                        currentScreen is Screen.Profile

                val totalCartCount = cartItems.sumOf { it.quantity }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        if (currentScreen is Screen.Home) {
                            OvisitoTopBar(
                                searchQuery = searchQuery,
                                onSearchChange = { viewModel.onSearchQueryChange(it) },
                                cartItemCount = totalCartCount,
                                onCartClick = { viewModel.navigateTo(Screen.Cart) }
                            )
                        }
                    },
                    bottomBar = {
                        if (isRootScreen) {
                            BottomNavBar(
                                currentTab = currentTab,
                                onTabSelected = { viewModel.selectTab(it) },
                                cartItemCount = totalCartCount
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = if (currentScreen is Screen.Home) innerPadding.calculateTopPadding() else 0.dp,
                                bottom = if (isRootScreen) innerPadding.calculateBottomPadding() else 0.dp
                            )
                    ) {
                        when (val screen = currentScreen) {
                            is Screen.Onboarding -> {
                                OnboardingScreen(
                                    onStartShopping = { viewModel.navigateTo(Screen.Home) }
                                )
                            }

                            is Screen.Home -> {
                                HomeScreen(
                                    products = products,
                                    categories = categories,
                                    selectedCategorySlug = selectedCategorySlug,
                                    onCategoryClick = { slug -> viewModel.selectCategory(slug) },
                                    onProductClick = { product -> viewModel.navigateTo(Screen.ProductDetail(product)) },
                                    onAddToCart = { product -> viewModel.addToCart(product) },
                                    onViewAllProducts = {
                                        viewModel.navigateTo(
                                            Screen.CategoryProducts("oleh-oleh-khas-aceh", "Oleh-oleh Khas Aceh")
                                        )
                                    }
                                )
                            }

                            is Screen.Categories -> {
                                CategoriesScreen(
                                    categories = categoryCards,
                                    onCategoryClick = { category ->
                                        viewModel.navigateTo(
                                            Screen.CategoryProducts(category.slug, category.name)
                                        )
                                    }
                                )
                            }

                            is Screen.CategoryProducts -> {
                                CategoryProductsScreen(
                                    title = screen.title,
                                    products = products,
                                    onBack = { viewModel.navigateTo(Screen.Categories) },
                                    onProductClick = { product -> viewModel.navigateTo(Screen.ProductDetail(product)) },
                                    onAddToCart = { product -> viewModel.addToCart(product) }
                                )
                            }

                            is Screen.ProductDetail -> {
                                val isWishlisted = wishlistIds.contains(screen.product.id)
                                ProductDetailScreen(
                                    product = screen.product,
                                    isWishlisted = isWishlisted,
                                    onBack = { viewModel.navigateTo(Screen.Home) },
                                    onToggleWishlist = { viewModel.toggleWishlist(screen.product.id) },
                                    onAddToCart = { qty -> viewModel.addToCart(screen.product, qty) },
                                    onBuyNow = { qty ->
                                        viewModel.addToCart(screen.product, qty)
                                        viewModel.navigateTo(Screen.Checkout)
                                    }
                                )
                            }

                            is Screen.Cart -> {
                                CartScreen(
                                    cartItems = cartItems,
                                    onUpdateQuantity = { id, delta -> viewModel.updateCartQuantity(id, delta) },
                                    onRemoveItem = { id -> viewModel.removeCartItem(id) },
                                    onToggleSelect = { id -> viewModel.toggleCartItemSelection(id) },
                                    onClearCart = { viewModel.clearCart() },
                                    onCheckout = { viewModel.navigateTo(Screen.Checkout) },
                                    onBack = { viewModel.selectTab(BottomTab.HOME) }
                                )
                            }

                            is Screen.Checkout -> {
                                CheckoutScreen(
                                    cartItems = cartItems.filter { it.isSelected },
                                    currentUser = currentUser,
                                    shippingMethods = shippingMethods,
                                    selectedShippingMethod = selectedShippingMethod,
                                    onSelectShippingMethod = { viewModel.selectShippingMethod(it) },
                                    selectedPaymentMethod = selectedPaymentMethod,
                                    onSelectPaymentMethod = { viewModel.selectPaymentMethod(it) },
                                    onPayNow = { viewModel.checkout() },
                                    onBack = { viewModel.navigateTo(Screen.Cart) }
                                )
                            }

                            is Screen.OrderSuccess -> {
                                OrderSuccessScreen(
                                    order = screen.order,
                                    onViewOrder = { viewModel.navigateTo(Screen.OrderDetail(screen.order)) },
                                    onBackToHome = { viewModel.selectTab(BottomTab.HOME) }
                                )
                            }

                            is Screen.Orders -> {
                                OrdersScreen(
                                    orders = orders,
                                    selectedFilter = selectedOrderFilter,
                                    onFilterChange = { viewModel.selectOrderFilter(it) },
                                    onOrderClick = { order -> viewModel.navigateTo(Screen.OrderDetail(order)) },
                                    onPayOrder = { order -> viewModel.payNow(order) },
                                    onCancelOrder = { orderNum -> viewModel.cancelOrder(orderNum) }
                                )
                            }

                            is Screen.OrderDetail -> {
                                OrderDetailScreen(
                                    order = screen.order,
                                    onBack = { viewModel.navigateTo(Screen.Orders) },
                                    onPayNow = { order -> viewModel.payNow(order) }
                                )
                            }

                            is Screen.Profile -> {
                                ProfileScreen(
                                    user = currentUser,
                                    onNavigateOrders = { viewModel.selectTab(BottomTab.ORDERS) },
                                    onNavigateWishlist = { viewModel.navigateTo(Screen.Wishlist) },
                                    onSyncBackend = { viewModel.syncBackend() },
                                    onUpdateCredentials = { id, secret -> viewModel.updateCredentials(id, secret) }
                                )
                            }

                            is Screen.Wishlist -> {
                                val wishlisted = products.filter { wishlistIds.contains(it.id) }
                                WishlistScreen(
                                    wishlistedProducts = wishlisted,
                                    onBack = { viewModel.navigateTo(Screen.Profile) },
                                    onProductClick = { product -> viewModel.navigateTo(Screen.ProductDetail(product)) },
                                    onAddToCart = { product -> viewModel.addToCart(product) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
