package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CategoryDisplayItem
import com.example.ui.theme.*

@Composable
fun CategoryIconButton(
    category: CategoryDisplayItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon: ImageVector = when (category.iconKey) {
        "all" -> Icons.Default.Apps
        "gift" -> Icons.Default.CardGiftcard
        "culinary" -> Icons.Default.Restaurant
        "fashion" -> Icons.Default.Checkroom
        "craft" -> Icons.Default.Palette
        "health" -> Icons.Default.LocalHospital
        "electronic" -> Icons.Default.Devices
        else -> Icons.Default.Category
    }

    val iconBgColor = if (isSelected) OvisitoTeal else OvisitoTealLight
    val iconTintColor = if (isSelected) Color.White else OvisitoTeal

    Column(
        modifier = modifier
            .width(68.dp)
            .clickable { onClick() }
            .testTag("category_btn_${category.slug}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = category.name,
                tint = iconTintColor,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = category.name,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) OvisitoTeal else TextPrimary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
