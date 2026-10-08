package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.BottomTab

@Composable
fun BottomNavBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    cartItemCount: Int,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .testTag("bottom_navigation_bar"),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        // 1. Beranda
        NavigationBarItem(
            selected = currentTab == BottomTab.HOME,
            onClick = { onTabSelected(BottomTab.HOME) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Beranda"
                )
            },
            label = {
                Text(
                    text = "Beranda",
                    fontSize = 11.sp,
                    fontWeight = if (currentTab == BottomTab.HOME) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OvisitoTeal,
                selectedTextColor = OvisitoTeal,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary,
                indicatorColor = OvisitoTealLight
            ),
            modifier = Modifier.testTag("tab_home")
        )

        // 2. Kategori
        NavigationBarItem(
            selected = currentTab == BottomTab.CATEGORIES,
            onClick = { onTabSelected(BottomTab.CATEGORIES) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomTab.CATEGORIES) Icons.Filled.GridView else Icons.Outlined.GridView,
                    contentDescription = "Kategori"
                )
            },
            label = {
                Text(
                    text = "Kategori",
                    fontSize = 11.sp,
                    fontWeight = if (currentTab == BottomTab.CATEGORIES) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OvisitoTeal,
                selectedTextColor = OvisitoTeal,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary,
                indicatorColor = OvisitoTealLight
            ),
            modifier = Modifier.testTag("tab_categories")
        )

        // 3. Keranjang (with badge)
        NavigationBarItem(
            selected = currentTab == BottomTab.CART,
            onClick = { onTabSelected(BottomTab.CART) },
            icon = {
                Box {
                    Icon(
                        imageVector = if (currentTab == BottomTab.CART) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                        contentDescription = "Keranjang"
                    )
                    if (cartItemCount > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 6.dp, y = (-4).dp)
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(StatusRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            },
            label = {
                Text(
                    text = "Keranjang",
                    fontSize = 11.sp,
                    fontWeight = if (currentTab == BottomTab.CART) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OvisitoTeal,
                selectedTextColor = OvisitoTeal,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary,
                indicatorColor = OvisitoTealLight
            ),
            modifier = Modifier.testTag("tab_cart")
        )

        // 4. Pesanan
        NavigationBarItem(
            selected = currentTab == BottomTab.ORDERS,
            onClick = { onTabSelected(BottomTab.ORDERS) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomTab.ORDERS) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
                    contentDescription = "Pesanan"
                )
            },
            label = {
                Text(
                    text = "Pesanan",
                    fontSize = 11.sp,
                    fontWeight = if (currentTab == BottomTab.ORDERS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OvisitoTeal,
                selectedTextColor = OvisitoTeal,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary,
                indicatorColor = OvisitoTealLight
            ),
            modifier = Modifier.testTag("tab_orders")
        )

        // 5. Akun
        NavigationBarItem(
            selected = currentTab == BottomTab.PROFILE,
            onClick = { onTabSelected(BottomTab.PROFILE) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Akun"
                )
            },
            label = {
                Text(
                    text = "Akun",
                    fontSize = 11.sp,
                    fontWeight = if (currentTab == BottomTab.PROFILE) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OvisitoTeal,
                selectedTextColor = OvisitoTeal,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary,
                indicatorColor = OvisitoTealLight
            ),
            modifier = Modifier.testTag("tab_profile")
        )
    }
}
