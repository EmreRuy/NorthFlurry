package com.example.wouple.activities.mainActivity.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.ui.theme.vintage

@Composable
fun DetailButton(
    onDetailsButtonClicked: () -> Unit,
) {
    val moccasin = Color(0xFFFFE4B5)
    var isPressed by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val colorTransition by infiniteTransition.animateColor(
        initialValue = vintage,
        targetValue = moccasin,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Button(
        modifier = Modifier.padding(bottom = 16.dp),
        shape = CircleShape,
        onClick = {
            isPressed = !isPressed
            onDetailsButtonClicked()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) {
                colorTransition
            } else {
                vintage
            },
            contentColor = Color.Black
        )
    ) {
        Text(
            text = stringResource(id = R.string.Forecast_Details)
        )
    }
}