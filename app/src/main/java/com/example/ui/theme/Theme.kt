package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SacredGold,
    onPrimary = TempleCharcoal,
    primaryContainer = SacredSaffronDark,
    onPrimaryContainer = SacredGoldContainer,
    secondary = SacredMarigold,
    onSecondary = TempleCharcoal,
    secondaryContainer = Color(0xFF42210B),
    onSecondaryContainer = SacredGoldContainer,
    tertiary = Color(0xFFFF8A65),
    background = Color(0xFF19120C),
    surface = Color(0xFF231A12),
    onBackground = Color(0xFFF7EFE8),
    onSurface = Color(0xFFF7EFE8),
    surfaceVariant = Color(0xFF33261C),
    onSurfaceVariant = Color(0xFFD6C5B8),
    outline = Color(0xFF5E4939)
)

private val LightColorScheme = lightColorScheme(
    primary = SacredSaffron,
    onPrimary = Color.White,
    primaryContainer = SaffronContainer,
    onPrimaryContainer = SacredSaffronDark,
    secondary = SacredVermillion,
    onSecondary = Color.White,
    secondaryContainer = SacredVermillionContainer,
    onSecondaryContainer = SacredVermillion,
    tertiary = SacredMarigold,
    background = SandalwoodIvory,
    surface = SandalwoodSurface,
    onBackground = TempleCharcoal,
    onSurface = TempleCharcoal,
    surfaceVariant = SandalwoodWarmSurface,
    onSurfaceVariant = TempleBrownMuted,
    outline = SandalwoodBorder
)

@Composable
fun PujaGharTheme(
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

