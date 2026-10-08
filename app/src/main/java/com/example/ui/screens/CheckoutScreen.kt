package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.theme.*

@Composable
fun CheckoutScreen(
    cartItems: List<CartItem>,
    currentUser: User,
    shippingMethods: List<ShippingMethod>,
    selectedShippingMethod: ShippingMethod,
    onSelectShippingMethod: (ShippingMethod) -> Unit,
    selectedPaymentMethod: String,
    onSelectPaymentMethod: (String) -> Unit,
    onPayNow: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val subtotal = cartItems.sumOf { it.subtotal }
    val shippingCost = selectedShippingMethod.costAmount
    val grandTotal = (subtotal + shippingCost).toLong()
    val formattedGrandTotal = "Rp %,d".format(grandTotal).replace(',', '.')

    var showAddressDialog by remember { mutableStateOf(false) }

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
                        text = "Checkout",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Pembayaran",
                            fontSize = 14.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = formattedGrandTotal,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = OvisitoTeal
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onPayNow,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("btn_bayar_sekarang"),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OvisitoGold,
                            contentColor = Color(0xFF422006)
                        )
                    ) {
                        Text(
                            text = "Bayar Sekarang",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        },
        modifier = modifier.testTag("checkout_screen")
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
            // 1. Alamat Pengiriman
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = OvisitoTeal,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Alamat Pengiriman",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Text(
                            text = "Ubah",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = OvisitoTeal,
                            modifier = Modifier.clickable { showAddressDialog = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${currentUser.address.name} (${currentUser.address.phone})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = currentUser.address.fullFormattedAddress,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }
            }

            // 2. Metode Pengiriman
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                            text = "Metode Pengiriman",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    shippingMethods.forEach { method ->
                        val isSelected = selectedShippingMethod.id == method.id
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onSelectShippingMethod(method) }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onSelectShippingMethod(method) },
                                    colors = RadioButtonDefaults.colors(selectedColor = OvisitoTeal)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Column {
                                    Text(
                                        text = method.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${method.description} • ${method.getFormattedCost()}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. Metode Pembayaran
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            tint = OvisitoTeal,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Metode Pembayaran",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Option 1: Transfer Bank
                    PaymentOptionRow(
                        title = "Transfer Bank (BCA, BRI, BNI, Mandiri)",
                        subtitle = "Verifikasi instan via Flip Sandbox",
                        icon = Icons.Default.AccountBalance,
                        isSelected = selectedPaymentMethod.startsWith("Transfer"),
                        onClick = { onSelectPaymentMethod("Transfer Bank (BCA, BRI, BNI, Mandiri)") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = CardBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Option 2: E-Wallet & QRIS
                    PaymentOptionRow(
                        title = "E-Wallet (OVO, DANA, GoPay, ShopeePay)",
                        subtitle = "Pindai QRIS & Flip Payment URL",
                        icon = Icons.Default.QrCodeScanner,
                        isSelected = selectedPaymentMethod.startsWith("E-Wallet"),
                        onClick = { onSelectPaymentMethod("E-Wallet (OVO, DANA, GoPay, ShopeePay)") }
                    )
                }
            }

            // 4. Ringkasan Pesanan
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ringkasan Pembayaran",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    SummaryRow(label = "Total Harga (${cartItems.sumOf { it.quantity }} barang)", value = "Rp %,d".format(subtotal.toLong()).replace(',', '.'))
                    Spacer(modifier = Modifier.height(6.dp))
                    SummaryRow(label = "Total Ongkos Kirim (${selectedShippingMethod.name})", value = selectedShippingMethod.getFormattedCost())
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = CardBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(label = "Total Belanja", value = formattedGrandTotal, isHighlight = true)
                }
            }
        }
    }
}

@Composable
fun PaymentOptionRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(if (isSelected) OvisitoTealLight else Color.Transparent)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) OvisitoTeal else BackgroundLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else OvisitoTeal,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }
        }

        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = OvisitoTeal)
        )
    }
}

@Composable
fun SummaryRow(label: String, value: String, isHighlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = if (isHighlight) 13.sp else 12.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlight) TextPrimary else TextSecondary
        )
        Text(
            text = value,
            fontSize = if (isHighlight) 14.sp else 12.sp,
            fontWeight = if (isHighlight) FontWeight.ExtraBold else FontWeight.Medium,
            color = if (isHighlight) OvisitoTeal else TextPrimary
        )
    }
}
