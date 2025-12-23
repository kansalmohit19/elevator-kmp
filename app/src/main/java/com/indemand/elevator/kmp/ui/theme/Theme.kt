package com.indemand.elevator.kmp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightAppColors = AppColors(
    background = Color(0xFFF5F6FA),
    title = Color(0xFF1F4BB7),
    elevatorLabelColor = Color(0xFF1F4BB7),
    elevatorBackground = Color(0xFFE3E9F4),
    elevatorIdle = Color(0xFF7E7E7E),
    elevatorMovingUp = Color(0xFF22C55E),
    elevatorMovingDown = Color(0xFFEF5C5C),
    buttonDefault = Color(0xFFC7D8FF),
    buttonTextColor = Color(0xFF113365)
)

private val DarkAppColors = AppColors(
    background = Color(0xFF0B1220),
    title = Color(0xFF7AA2F7),
    elevatorLabelColor = Color(0xFF7AA2F7),
    elevatorBackground = Color(0xFF1E293B),
    elevatorIdle = Color(0xFF9CA3AF),
    elevatorMovingUp = Color(0xFF4ADE80),
    elevatorMovingDown = Color(0xFFF87171),
    buttonDefault = Color(0xFF1F3A8A),
    buttonTextColor = Color(0xFFDCE7FF)
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40, secondary = PurpleGrey40, tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ElevatorkmpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val appColors = if (darkTheme) DarkAppColors else LightAppColors

    CompositionLocalProvider(
        LocalAppColors provides appColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme, typography = Typography, content = content
        )
    }
}