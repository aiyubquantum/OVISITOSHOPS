package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CategoryDisplayItem
import com.example.data.model.Product
import com.example.ui.components.CategoryIconButton
import com.example.ui.components.ProductGridCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    products: List<Product>,
    categories: List<CategoryDisplayItem>,
    selectedCategorySlug: String,
    onCategoryClick: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    onViewAllProducts: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Promo Hero Banner
        item(span = { GridItemSpan(2) }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(180.dp)
                    .testTag("hero_promo_banner"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.banner_aceh_souvenirs),
                        contentDescription = "Oleh-oleh Khas Aceh",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Semi-transparent overlay gradient
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xEE0A656F),
                                        Color(0x990A656F),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // Banner Content
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .widthIn(max = 240.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Oleh-oleh Khas Aceh\nUntuk Orang Tersayang",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Rasa, budaya, dan cerita dari Tanah Rencong",
                            color = Color(0xFFE2F3F5),
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onViewAllProducts,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OvisitoGold,
                                contentColor = Color(0xFF422006)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = "Belanja Sekarang",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Categories Circular Icons
        item(span = { GridItemSpan(2) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp)
            ) {
                // Row 1 (first 4 categories)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    categories.take(4).forEach { cat ->
                        CategoryIconButton(
                            category = cat,
                            isSelected = selectedCategorySlug == cat.slug,
                            onClick = { onCategoryClick(cat.slug) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 2 (next 4 categories)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    categories.drop(4).take(4).forEach { cat ->
                        CategoryIconButton(
                            category = cat,
                            isSelected = selectedCategorySlug == cat.slug,
                            onClick = { onCategoryClick(cat.slug) }
                        )
                    }
                }
            }
        }

        // 3. Section Title: Produk Unggulan
        item(span = { GridItemSpan(2) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Produk Unggulan",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onViewAllProducts() }
                ) {
                    Text(
                        text = "Lihat Semua",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OvisitoTeal
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = OvisitoTeal,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        // 4. Products Grid
        items(products) { product ->
            Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                ProductGridCard(
                    product = product,
                    onClick = { onProductClick(product) },
                    onAddToCart = { onAddToCart(product) }
                )
            }
        }
    }
}
