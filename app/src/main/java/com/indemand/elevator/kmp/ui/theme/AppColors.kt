package com.indemand.elevator.kmp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val title: Color,
    val elevatorLabelColor: Color,
    val elevatorBackground: Color,
    val elevatorIdle: Color,
    val elevatorMovingUp: Color,
    val elevatorMovingDown: Color,
    val buttonDefault: Color,
    val buttonTextColor: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        background = Color.Unspecified,
        title = Color.Unspecified,
        elevatorLabelColor = Color.Unspecified,
        elevatorBackground = Color.Unspecified,
        elevatorIdle = Color.Unspecified,
        elevatorMovingUp = Color.Unspecified,
        elevatorMovingDown = Color.Unspecified,
        buttonDefault = Color.Unspecified,
        buttonTextColor = Color.Unspecified
    )
}

