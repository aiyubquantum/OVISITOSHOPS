package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Order
import com.example.ui.theme.*

@Composable
fun OrdersScreen(
    orders: List<Order>,
    selectedFilter: String,
    onFilterChange: (String) -> Unit,
    onOrderClick: (Order) -> Unit,
    onPayOrder: (Order) -> Unit,
    onCancelOrder: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filterTabs = listOf("Semua", "Menunggu Pembayaran", "Diproses", "Dikirim", "Selesai", "Dibatalkan")

    val filteredOrders = remember(orders, selectedFilter) {
        if (selectedFilter == "Semua") orders
        else {
            orders.filter { order ->
                when (selectedFilter) {
                    "Menunggu Pembayaran" -> order.status == "pending"
                    "Diproses" -> order.status == "processing"
                    "Dikirim" -> order.status == "shipped"
                    "Selesai" -> order.status == "completed"
                    "Dibatalkan" -> order.status == "cancelled"
                    else -> true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("orders_screen")
    ) {
        // Top App Bar
        Surface(
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = "Pesanan Saya",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // Scrollable Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filterTabs.forEach { tab ->
                        val isSelected = selectedFilter == tab
                        FilterChip(
                            selected = isSelected,
                            onClick = { onFilterChange(tab) },
                            label = {
                                Text(
                                    text = tab,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = OvisitoTeal,
                                selectedLabelColor = Color.White,
                                containerColor = BackgroundLight,
                                labelColor = TextPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = CardBorder,
                                selectedBorderColor = OvisitoTeal
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }
        }

        if (filteredOrders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TextMuted
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Tidak Ada Pesanan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pesanan dengan status '$selectedFilter' tidak ditemukan",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredOrders) { order ->
                    OrderCard(
                        order = order,
                        onClick = { onOrderClick(order) },
                        onPay = { onPayOrder(order) },
                        onCancel = { onCancelOrder(order.orderNumber) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    onClick: () -> Unit,
    onPay: () -> Unit,
    onCancel: () -> Unit
) {
    val statusColor = when (order.status) {
        "pending" -> OvisitoGoldDark
        "processing" -> StatusBlue
        "shipped" -> OvisitoTeal
        "completed" -> StatusGreen
        "cancelled" -> StatusRed
        else -> TextSecondary
    }

    val statusBg = when (order.status) {
        "pending" -> Color(0xFFFEF3C7)
        "processing" -> StatusBlueLight
        "shipped" -> OvisitoTealLight
        "completed" -> StatusGreenLight
        "cancelled" -> StatusRedLight
        else -> BackgroundLight
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("order_card_${order.orderNumber}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp, brush = androidx.compose.ui.graphics.SolidColor(CardBorder))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: No Pesanan & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = order.orderNumber,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = order.orderedAt ?: "12 Sep 2025",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = order.statusLabel,
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = CardBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // First item preview
            if (order.items.isNotEmpty()) {
                val firstItem = order.items.first()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BackgroundLight)
                    ) {
                        if (firstItem.product?.localDrawableRes != null) {
                            Image(
                                painter = painterResource(id = firstItem.product.localDrawableRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ovisito_shop_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = firstItem.productName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${firstItem.quantity} barang • ${firstItem.formattedPrice ?: firstItem.price}",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                        if (order.items.size > 1) {
                            Text(
                                text = "+${order.items.size - 1} produk lainnya",
                                fontSize = 10.sp,
                                color = OvisitoTeal,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Total amount & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Belanja",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = order.formattedTotal,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OvisitoTeal
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (order.status == "pending") {
                        OutlinedButton(
                            onClick = onCancel,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusRed),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(StatusRed)
                            ),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "Batalkan", fontSize = 11.sp)
                        }

                        Button(
                            onClick = onPay,
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OvisitoGold),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = "Bayar",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF422006)
                            )
                        }
                    } else if (order.trackingNumber != null) {
                        Button(
                            onClick = onClick,
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OvisitoTeal),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Lacak", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
