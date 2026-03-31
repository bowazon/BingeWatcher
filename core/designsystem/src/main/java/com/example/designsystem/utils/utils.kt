package com.example.designsystem.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun getReadableTextColor(bgColor: Color): Color {
    val luminance = bgColor.luminance()
    return if (luminance > 0.5f) Color.Black else Color.White
}