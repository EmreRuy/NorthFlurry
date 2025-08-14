package com.example.wouple.activities.firstScreen

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.ui.theme.AppTheme
import androidx.core.net.toUri

@Preview(showBackground = true, name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewFirstScreenView() {
    AppTheme(darkTheme = false) {
        FirstScreenView(onStartButtonClicked = { /* No action for preview */ })
    }
    AppTheme(darkTheme = true) {
        FirstScreenView(onStartButtonClicked = { /* No action for preview */ })
    }
}

@Composable
fun FirstScreenView(
    onStartButtonClicked: () -> Unit
) {
    val currentContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icondrop),
                contentDescription = "logo",
                // Use sizeIn to constrain the image size, making it responsive
                modifier = Modifier.sizeIn(maxHeight = 150.dp, maxWidth = 150.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.WeatherForecast),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(CenterHorizontally),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.AppExplainer),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        // The button and privacy policy link are at the bottom
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { onStartButtonClicked() }
            ) {
                Text(
                    text = stringResource(id = R.string.GetStartButton),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.surface
                    )
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        val url = "https://sites.google.com/view/northflurry/home"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = url.toUri()
                        currentContext.startActivity(intent)
                    },
                text = stringResource(id = R.string.NorthPrivacy),
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}