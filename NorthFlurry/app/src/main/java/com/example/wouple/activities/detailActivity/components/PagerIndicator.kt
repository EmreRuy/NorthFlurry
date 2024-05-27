package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wouple.ui.theme.PagerColor

@Composable
fun PagerIndicator(step: Int, totalSteps: Int) {

    @Composable
    fun Dot(isSelected: Boolean) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
                .clip(CircleShape)
                .background(
                    brush = if (isSelected) {
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFADD8E6), Color(0xFF87CEEB))
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(PagerColor, Color.Transparent)
                        )
                    }
                )
                .width(if (isSelected) 14.dp else 8.dp)
                .height(8.dp)
        )
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(totalSteps) {
            if (it == step) {
                Dot(true)
            } else {
                Dot(false)
            }
        }
    }
}