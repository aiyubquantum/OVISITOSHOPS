package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = OvisitoTeal,
    onPrimary = Color.White,
    primaryContainer = OvisitoTealDark,
    onPrimaryContainer = OvisitoTealLight,
    secondary = OvisitoGold,
    onSecondary = Color.Black,
    secondaryContainer = OvisitoOrange,
    tertiary = OvisitoYellow,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

private val LightColorScheme = lightColorScheme(
    primary = OvisitoTeal,
    onPrimary = Color.White,
    primaryContainer = OvisitoTealLight,
    onPrimaryContainer = OvisitoTealDark,
    secondary = OvisitoGold,
    onSecondary = Color(0xFF422006),
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = Color(0xFF78350F),
    tertiary = OvisitoOrange,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextSecondary,
    outline = CardBorder
)

@Composable
fun OvisitoShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
