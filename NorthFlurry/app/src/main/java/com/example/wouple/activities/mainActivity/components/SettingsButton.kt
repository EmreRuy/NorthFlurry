package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wouple.R

@Composable
fun SettingsButton(
    onSettingsClicked: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    isExpanded = rememberUpdatedState(isExpanded).value
    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .clip(CircleShape)
    ) {
        IconButton(
            onClick = {
                isPressed = !isPressed
                onSettingsClicked()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = Color.White
            )
        }
    }
}