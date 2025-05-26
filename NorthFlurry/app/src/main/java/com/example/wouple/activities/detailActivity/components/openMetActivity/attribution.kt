package com.example.wouple.activities.detailActivity.components.openMetActivity

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import androidx.core.net.toUri

@Preview(showBackground = true, name = "Attribution View")
@Composable
fun PreviewGetAttributionForOpenMet() {
    val mockLocation = SearchedLocation(
        display_name = "Oslo, Norway",
        lat = 59.9139.toString(),
        lon = 10.7522.toString()
    )
    MaterialTheme {
        GetAttributionForOpenMet(searchedLocation = mockLocation)
    }
}

@Composable
fun GetAttributionForOpenMet(searchedLocation: SearchedLocation) {
    val attributionContext = LocalContext.current
    val openMetUrl = rememberUpdatedState("https://open-meteo.com/")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background( Color(0xFF1F2B2F))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.locationpin),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = getProperDisplayName(searchedLocation.display_name) ?: "N/D",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp),
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.WeatherDataBy),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp),
                )
                Text(
                    text = stringResource(id = R.string.OpenMeteoCom),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, openMetUrl.value.toUri())
                        attributionContext.startActivity(intent)
                    }
                )
            }
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()
