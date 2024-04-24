package com.example.wouple.elements

import android.graphics.drawable.Drawable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.beige
import com.example.wouple.ui.theme.getSecondaryGradients
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            ),
    )
}

@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            beige
        } else {
            Color.Black
        },
        animationSpec = tween(easing = LinearEasing), label = "",
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun CustomTabForSettings(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 120.dp,
    onClick: (index: Int) -> Unit,
    tabHeight: Dp = 40.dp,
    temp: TemperatureResponse,
) {
    val numItems = items.size
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing), label = "",
    )
    val isDay = temp.current_weather.is_day == 1
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Color.White)
            .height(tabHeight)
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = if (isDay) Color(0xFF324BBA) else Color(0xFF495BB8),
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(CircleShape)
                .padding(top = 2.dp),
        ) {
            items.forEachIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                val lowerCasedText = text.lowercase().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()}
                MyTabItem(
                    isSelected = isSelected,
                    onClick = {
                        onClick(index)
                    },
                    tabWidth = tabWidth,
                    text = lowerCasedText,
                )
            }
        }
        if (numItems == 2) {
            Icon(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp) // Adjust the padding as needed
                    .align(Alignment.CenterEnd)
                   // .clickable  }
                    .size(32.dp),
                tint = Color.Unspecified
            )
        }
    }
}
