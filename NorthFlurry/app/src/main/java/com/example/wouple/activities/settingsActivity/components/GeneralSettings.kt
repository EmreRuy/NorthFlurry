package com.example.wouple.activities.settingsActivity.components

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import androidx.core.net.toUri


@Composable
fun SettingsItemCard(
    painter: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SettingsCardOne() {
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(
        text = stringResource(id = R.string.GeneralSettings),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        fontSize = 22.sp
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun TroubleOnAppSettings(onTroubleWithAppClicked: () -> Unit) {
    SettingsItemCard(
        painter = painterResource(id = R.drawable.ic_warning_triangle),
        text = stringResource(id = R.string.TroubleWithTheApp),
        onClick = onTroubleWithAppClicked
    )
}

@Composable
fun IdeasSettings(onIdeaClicked: () -> Unit) {
    SettingsItemCard(
        painter = painterResource(id = R.drawable.ic_lightbulb),
        text = stringResource(id = R.string.AnyGoodIdeas),
        onClick = onIdeaClicked
    )
}

@Composable
fun ShareTheAppSettings() {
    val context = LocalContext.current
    SettingsItemCard(
        painter = painterResource(id = R.drawable.ic_world),
        text = stringResource(id = R.string.ShareTheApp),
        onClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=com.weather.wouple&hl=en_US"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    )
}

@Composable
fun RateUsSettings() {
    val context = LocalContext.current
    SettingsItemCard(
        painter = painterResource(id = R.drawable.heartone),
        text = stringResource(id = R.string.RateUs),
        onClick = {
            val appURL =
                "https://play.google.com/store/apps/details?id=com.weather.wouple&hl=en_US"
            val playIntent: Intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = appURL.toUri()
            }
            try {
                context.startActivity(playIntent)
            } catch (e: Exception) {
                Log.e("TAG", "Error opening URL: ${e.message}")
            }
        }
    )
}

@Composable
fun LottieFilesAndTerms(onLottieClicked: () -> Unit) {
    SettingsItemCard(
        painter = painterResource(id = R.drawable.lottieicon),
        text = stringResource(id = R.string.LottieFilesAndTerms),
        onClick = onLottieClicked
    )
}

@Composable
fun OpenMetAttribution(onMetClicked: () -> Unit) {
    SettingsItemCard(
        painter = painterResource(id = R.drawable.cc),
        text = stringResource(id = R.string.CCLicence),
        onClick = onMetClicked
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            SettingsCardOne()
            TroubleOnAppSettings(onTroubleWithAppClicked = {})
            IdeasSettings(onIdeaClicked = {})
            ShareTheAppSettings()
            RateUsSettings()
            LottieFilesAndTerms(onLottieClicked = {})
            OpenMetAttribution(onMetClicked = {})
        }
    }
}
