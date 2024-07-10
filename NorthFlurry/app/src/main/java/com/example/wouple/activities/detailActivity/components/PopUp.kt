package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.mocassin

@Composable
fun PopUpView(temp: TemperatureResponse) {
    var popupVisible by remember { mutableStateOf(false) }
    if (popupVisible) {
        PopUpContent(
            title = stringResource(id = R.string.The_Ultraviolet_Index),
            text = stringResource(id = R.string.Explainer_Of_Uv_Index),
            onDismiss = { popupVisible = false },
            temp = temp
        )
    }
    IconButton(onClick = { popupVisible = true }) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Show Popup",
            tint = Color.White
        )
    }
}

@Composable
fun PopUpContent(title: String, text: String, onDismiss: () -> Unit, temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Surface(
                modifier = Modifier
                    .padding(24.dp),
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                color = if (isDay) Color(0xFF586FCE) else Color(0xFF3F5066) //Color(0xFF2E3A59)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = mocassin
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        color = Color(0xFFE0E0E0)
                    )
                }
            }
        }
    )
}