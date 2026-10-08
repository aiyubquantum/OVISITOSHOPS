package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.User
import com.example.data.network.ApiClient
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    user: User,
    onNavigateOrders: () -> Unit,
    onNavigateWishlist: () -> Unit,
    onSyncBackend: () -> Unit,
    onUpdateCredentials: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showInfoDialog by remember { mutableStateOf<String?>(null) }
    var showApiConfigDialog by remember { mutableStateOf(false) }

    var clientIdInput by remember { mutableStateOf(ApiClient.customClientId) }
    var clientSecretInput by remember { mutableStateOf(ApiClient.customClientSecret) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp)
            .testTag("profile_screen")
    ) {
        // User Profile Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(OvisitoTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = user.name,
                        tint = Color.White,
                        modifier = Modifier.size(38.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = user.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = user.phone,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(OvisitoTealLight)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Member sejak ${user.memberSince}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OvisitoTeal
                        )
                    }
                }
            }
        }

        // Live Backend Status & Sync Action Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(StatusGreen)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Status Backend Ovisito v2.6",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Text(
                        text = "Aktif",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = StatusGreen
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Endpoint: https://api.ovisito.com/api/v2\nKatalog real souvenir Aceh & sistem Flip PG siap terhubung.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onSyncBackend,
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OvisitoTeal)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Sync Backend", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { showApiConfigDialog = true },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OvisitoTeal),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(OvisitoTeal)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "API Key / Auth", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Menu Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                ProfileMenuItem(
                    icon = Icons.Default.ReceiptLong,
                    title = "Pesanan Saya",
                    onClick = onNavigateOrders
                )
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.FavoriteBorder,
                    title = "Wishlist",
                    onClick = onNavigateWishlist
                )
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.LocationOn,
                    title = "Alamat Saya",
                    onClick = { showInfoDialog = "Alamat Pengiriman:\n${user.address.fullFormattedAddress}" }
                )
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Payment,
                    title = "Metode Pembayaran",
                    onClick = { showInfoDialog = "Metode Pembayaran Aktif:\n• QRIS (Flip PG)\n• Virtual Account BCA, Mandiri, BRI, BNI\n• E-Wallet (GoPay, OVO, DANA)" }
                )
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.HelpOutline,
                    title = "Pusat Bantuan",
                    onClick = { showInfoDialog = "Pusat Bantuan Ovisito Shop:\nEmail: ceo@ovisito.com\nWhatsApp CS: +62 877 8878 889" }
                )
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Info,
                    title = "Tentang Ovisito Shop",
                    onClick = { showInfoDialog = "Ovisito Shop v2.6\nPlatform E-Commerce Oleh-oleh dan Kerajinan Khas Aceh.\n'Mudah Belanja, Makin Dekat ke Aceh'" }
                )
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    title = "Pengaturan",
                    onClick = { showInfoDialog = "Pengaturan Aplikasi:\nNotifikasi Pesanan: Aktif\nTema: Mode Terang" }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Official Logo Banner in Profile Footer
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ovisito_shop_logo),
                    contentDescription = "Ovisito Shop",
                    modifier = Modifier.height(90.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "See more, Smile more",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = OvisitoTeal
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Flight,
                        contentDescription = null,
                        tint = OvisitoGold,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

    // Info Dialog
    showInfoDialog?.let { info ->
        AlertDialog(
            onDismissRequest = { showInfoDialog = null },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = null }) {
                    Text("OK", color = OvisitoTeal, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text(text = "Informasi", fontWeight = FontWeight.Bold, color = TextPrimary)
            },
            text = {
                Text(text = info, color = TextSecondary, fontSize = 13.sp)
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // API Config Dialog
    if (showApiConfigDialog) {
        AlertDialog(
            onDismissRequest = { showApiConfigDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateCredentials(clientIdInput.trim(), clientSecretInput.trim())
                        showApiConfigDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OvisitoTeal)
                ) {
                    Text("Simpan & Sinkron", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showApiConfigDialog = false }) {
                    Text("Batal")
                }
            },
            title = {
                Text(text = "Konfigurasi Backend API", fontWeight = FontWeight.Bold, color = TextPrimary)
            },
            text = {
                Column {
                    Text(
                        text = "Masukkan kredensial X-Client-ID dan X-Client-Secret dari tim backend Ovisito:",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = clientIdInput,
                        onValueChange = { clientIdInput = it },
                        label = { Text("X-Client-ID") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = clientSecretInput,
                        onValueChange = { clientSecretInput = it },
                        label = { Text("X-Client-Secret") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OvisitoTeal,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(14.dp)
        )
    }
}
