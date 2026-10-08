package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Product
import com.example.ui.theme.*

@Composable
fun ProductDetailScreen(
    product: Product,
    isWishlisted: Boolean,
    onBack: () -> Unit,
    onToggleWishlist: () -> Unit,
    onAddToCart: (Int) -> Unit,
    onBuyNow: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var quantity by remember { mutableIntStateOf(1) }

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
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = TextPrimary
                        )
                    }

                    Row {
                        IconButton(onClick = { /* Share */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "Bagikan",
                                tint = TextPrimary
                            )
                        }
                        IconButton(onClick = onToggleWishlist) {
                            Icon(
                                imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorit",
                                tint = if (isWishlisted) StatusRed else TextPrimary
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                ) {
                    // Quantity selector row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Jumlah",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Kurang",
                                    modifier = Modifier.size(16.dp),
                                    tint = if (quantity > 1) TextPrimary else TextMuted
                                )
                            }

                            Text(
                                text = quantity.toString(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            IconButton(
                                onClick = { if (quantity < product.stock) quantity++ },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambah",
                                    modifier = Modifier.size(16.dp),
                                    tint = TextPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Buttons: + Keranjang & Beli Sekarang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onAddToCart(quantity) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_add_to_cart_detail"),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = OvisitoTeal
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(OvisitoTeal)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "+ Keranjang",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Button(
                            onClick = { onBuyNow(quantity) },
                            modifier = Modifier
                                .weight(1.2f)
                                .height(48.dp)
                                .testTag("btn_buy_now_detail"),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OvisitoGold,
                                contentColor = Color(0xFF422006)
                            )
                        ) {
                            Text(
                                text = "Beli Sekarang",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.testTag("product_detail_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(BackgroundLight)
            ) {
                val imageUrl = product.getImageUrl()
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (product.localDrawableRes != null) {
                    Image(
                        painter = painterResource(id = product.localDrawableRes),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(OvisitoTealLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ovisito_shop_logo),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }

                // Badges overlay
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (product.isBestSeller) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(OvisitoGold)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Best Seller",
                                color = Color(0xFF451A03),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    if (product.isLocalAceh) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(StatusGreenLight)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Produk Lokal",
                                color = Color(0xFF065F46),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Info Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Rating & Review Count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = OvisitoYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "%.1f".format(product.rating),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${if (product.reviewCount > 999) "%.1fk".format(product.reviewCount / 1000.0) else product.reviewCount} ulasan)",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Price
                Text(
                    text = product.getFormattedPrice(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = OvisitoTeal
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = CardBorder, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // Deskripsi
                Text(
                    text = "Deskripsi",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = product.description ?: "Produk khas Aceh berkualitas tinggi, diolah dengan bahan alami dan resep warisan leluhur Tanah Rencong.",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Guarantees badges row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeaturePill(text = "100% Original", icon = Icons.Default.Verified)
                    FeaturePill(text = "Produk Lokal Aceh", icon = Icons.Default.LocationOn)
                    FeaturePill(text = "Halal", icon = Icons.Default.CheckCircle)
                }
            }
        }
    }
}

@Composable
fun FeaturePill(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundLight)
            .border(0.5.dp, CardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OvisitoTeal,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}
