package com.example.wouple.activities.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getBackgroundGradient(): List<Color>{
  val colors = listOf(
        Color(0xFF1D244D),
        Color(0xFF25508C),
        Color(0xFF4180B3),
        Color(0xFF8ABFCC),
        Color(0xFFC0DDE1),
        Color(0xFFFFE4B5),
    )
    return colors
}
