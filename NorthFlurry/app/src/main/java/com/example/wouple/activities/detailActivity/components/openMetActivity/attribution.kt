package com.example.wouple.activities.detailActivity.components.openMetActivity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun GetAttributionForOpenMeteo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 2.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        val context = LocalContext.current
        Row {
            BasicText(
                text = "Weather data by ",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 4.dp)
            )
            BasicText(
                text = "open-meteo.com",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable {
                        val url = "https://open-meteo.com/"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        context.startActivity(intent)
                    }
            )
        }
    }
}