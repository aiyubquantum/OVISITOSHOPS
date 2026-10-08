package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Order
import com.example.ui.theme.*

@Composable
fun OrderDetailScreen(
    order: Order,
    onBack: () -> Unit,
    onPayNow: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    Scaffold(
        topBar = {
            Surface(
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = TextPrimary
                        )
                    }
                    Text(
                        text = "Detail Pesanan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
        },
        bottomBar = {
            if (order.status == "pending") {
                Surface(
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Pembayaran",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = order.formattedTotal,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = OvisitoTeal
                            )
                        }

                        Button(
                            onClick = { onPayNow(order) },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OvisitoGold)
                        ) {
                            Text(
                                text = "Bayar Sekarang",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF422006)
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.testTag("order_detail_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Status: ${order.statusLabel}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = OvisitoTeal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No. Pesanan: ${order.orderNumber}",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    if (order.trackingNumber != null) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "No. Resi (${order.courier?.uppercase()}): ${order.trackingNumber}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }
            }

            // Tracking History (if any)
            if (order.history.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = null,
                                tint = OvisitoTeal,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Lacak Pengiriman",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        order.history.forEachIndexed { index, track ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(if (index == 0) OvisitoTeal else CardBorder)
                                    )
                                    if (index < order.history.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .width(2.dp)
                                                .height(36.dp)
                                                .background(CardBorder)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.padding(bottom = 12.dp)) {
                                    Text(
                                        text = track.description,
                                        fontSize = 12.sp,
                                        fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${track.location} • ${track.trackedAt}",
                                        fontSize = 10.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Items Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Daftar Produk",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    order.items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(BackgroundLight)
                            ) {
                                if (item.product?.localDrawableRes != null) {
                                    Image(
                                        painter = painterResource(id = item.product.localDrawableRes),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.ovisito_shop_logo),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .fillMaxSize()
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.productName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "${item.quantity} x ${item.formattedPrice ?: item.price}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }

                            Text(
                                text = item.formattedSubtotal ?: item.subtotal,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = OvisitoTeal
                            )
                        }
                    }
                }
            }

            // Shipping Address
            if (order.shippingAddress != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Alamat Pengiriman",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${order.shippingAddress.name} (${order.shippingAddress.phone})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = order.shippingAddress.fullFormattedAddress,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}
