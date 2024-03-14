package com.example.wouple.elements

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.orgn

@Composable
fun RowScope.CustomPrecipitationBarChart(
    size: Float,
    max: Float
) {
    val context = LocalContext.current
    var height by remember{ mutableStateOf(0f) }
    val heightStateAnimate by animateDpAsState(
        targetValue = height.dp,
        tween(2000, delayMillis = 300, easing = LinearEasing), label = ""
    )
    LaunchedEffect(key1 = size ){
        height = size *20
    }
    Box(
        modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 4.dp)
            .size(heightStateAnimate)
            .weight(1f)
            .border(BorderStroke(1.dp, color = Color.Transparent))
            .background(Spiro, shape = RoundedCornerShape(10.dp))
            .clickable {
                Toast.makeText(context, "UV: $size", Toast.LENGTH_SHORT).show()
            }
    )
}
