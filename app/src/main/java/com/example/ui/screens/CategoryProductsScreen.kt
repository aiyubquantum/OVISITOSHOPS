package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.components.ProductListItem
import com.example.ui.theme.*

@Composable
fun CategoryProductsScreen(
    title: String,
    products: List<Product>,
    onBack: () -> Unit,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val chips = listOf("Semua", "Kopi", "Kue & Camilan", "Kerajinan", "Herbal")
    var selectedChip by remember { mutableStateOf("Semua") }

    val filteredProducts = remember(products, selectedChip) {
        if (selectedChip == "Semua") products
        else {
            products.filter { p ->
                when (selectedChip) {
                    "Kopi" -> p.name.contains("Kopi", ignoreCase = true)
                    "Kue & Camilan" -> p.name.contains("Kue", ignoreCase = true) || p.name.contains("Pisang", ignoreCase = true)
                    "Kerajinan" -> p.categoryId == "cat_kerajinan" || p.name.contains("Bros", ignoreCase = true) || p.name.contains("Pinto", ignoreCase = true)
                    "Herbal" -> p.categoryId == "cat_kesehatan" || p.name.contains("Nilam", ignoreCase = true) || p.name.contains("Kurma", ignoreCase = true)
                    else -> true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("category_products_screen")
    ) {
        // Top App Bar
        Surface(
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }

        // Subcategory Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            chips.forEach { chip ->
                val isSelected = selectedChip == chip
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedChip = chip },
                    label = {
                        Text(
                            text = chip,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
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
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredProducts) { product ->
                ProductListItem(
                    product = product,
                    onClick = { onProductClick(product) },
                    onAddToCart = { onAddToCart(product) }
                )
            }
        }
    }
}
