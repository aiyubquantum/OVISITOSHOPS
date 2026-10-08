package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@Composable
fun OvisitoTopBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    cartItemCount: Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // Brand Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ovisito Logo Brand
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.ovisito_shop_logo),
                    contentDescription = "Ovisito Shop",
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                    modifier = Modifier.height(60.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Makin Dekat ke Aceh",
                        fontSize = 11.sp,
                        color = OvisitoTeal,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Belanja Oleh-Oleh Khas",
                        fontSize = 9.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Actions: Notif & Cart with Badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { /* Notifications */ },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = TextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onCartClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Keranjang Belanja",
                        tint = OvisitoTeal,
                        modifier = Modifier.size(24.dp)
                    )
                    if (cartItemCount > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-2).dp, y = 2.dp)
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(StatusRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("search_input_field"),
            placeholder = {
                Text(
                    text = "Cari produk, oleh-oleh, atau toko...",
                    fontSize = 13.sp,
                    color = TextMuted
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Cari",
                    tint = OvisitoTeal,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Hapus pencarian",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OvisitoTeal,
                unfocusedBorderColor = CardBorder,
                focusedContainerColor = BackgroundLight,
                unfocusedContainerColor = BackgroundLight
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { /* do search */ })
        )
    }
}
