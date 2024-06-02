package com.example.wouple.activities.mainActivity.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.ui.theme.mocassin
import com.example.wouple.ui.theme.vintage

@Composable
fun DetailButton(
    onDetailsButtonClicked: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val colorTransition by infiniteTransition.animateColor(
        initialValue = vintage,
        targetValue = mocassin,
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
            backgroundColor = if (isPressed) {
                colorTransition
            } else {
                vintage
            }
        )
    ) {
        Text(
            text = stringResource(id = R.string.Forecast_Details)
        )
    }
}