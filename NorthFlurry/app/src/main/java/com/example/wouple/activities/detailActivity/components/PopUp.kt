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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.wouple.R

@Preview(showBackground = true, name = "Popup View")
@Composable
fun PreviewPopUpView() {
    MaterialTheme {
        PopUpView()
    }
}

@Preview(showBackground = true, name = "Popup Content")
@Composable
fun PreviewPopUpContent() {

    MaterialTheme {
        PopUpContent(
            title = "The Ultraviolet Index",
            text = "This index represents the intensity of UV radiation from the sun.",
            onDismiss = {},
        )
    }
}

@Composable
fun PopUpView() {
    var popupVisible by remember { mutableStateOf(false) }
    if (popupVisible) {
        PopUpContent(
            title = stringResource(id = R.string.The_Ultraviolet_Index),
            text = stringResource(id = R.string.Explainer_Of_Uv_Index),
            onDismiss = { popupVisible = false }
        )
    }
    IconButton(onClick = { popupVisible = true }) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Show Popup",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PopUpContent(title: String, text: String, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Surface(
                modifier = Modifier
                    .padding(24.dp),
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}