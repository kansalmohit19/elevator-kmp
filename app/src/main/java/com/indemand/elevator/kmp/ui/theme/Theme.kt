package com.indemand.elevator.kmp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ---------- Light Theme ----------
val LightPrimary = Color(0xFF2563EB)      // Blue 600
val LightSecondary = Color(0xFF38BDF8)    // Sky Blue
val LightBackground = Color(0xFFF9FAFB)   // Almost White
val LightSurface = Color(0xFFFFFFFF)      // Pure White
val LightCard = Color(0xFF82A7FF)          // Subtle Gray
val LightOutline = Color(0xFFE5E7EB)       // Divider Gray

val LightOnPrimary = Color.White
val LightOnBackground = Color(0xFF111827) // Almost Black
val LightOnSurface = Color(0xFF1F2937)

// ---------- Dark Theme ----------
val DarkPrimary = Color(0xFF60A5FA)       // Blue 400
val DarkSecondary = Color(0xFF38BDF8)     // Sky Blue
val DarkBackground = Color(0xFF020617)    // Deep Navy
val DarkSurface = Color(0xFF0F172A)       // Card Background
val DarkCard = Color(0xFF1E293B)          // Elevated Surface
val DarkOutline = Color(0xFF334155)       // Subtle Borders

val DarkOnPrimary = Color(0xFF020617)
val DarkOnBackground = Color(0xFFE5E7EB)
val DarkOnSurface = Color(0xFFCBD5F5)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightCard,
    onPrimary = LightOnPrimary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    outline = LightOutline
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkCard,
    onPrimary = DarkOnPrimary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    outline = DarkOutline
)

@Composable
fun ElevatorkmpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}