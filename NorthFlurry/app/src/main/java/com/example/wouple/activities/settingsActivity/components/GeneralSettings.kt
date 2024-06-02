package com.example.wouple.activities.settingsActivity.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.wouple.R
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.beige

@Composable
fun SettingsCardOne() {
    Spacer(modifier = Modifier.padding(top = 12.dp))
    Text(
        text = stringResource(id = R.string.GeneralSettings),
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Medium,
        color = beige.copy(alpha = 0.8f),
        fontSize = 28.sp
    )
}

@Composable
fun TroubleOnAppSettings(onTroubleWithAppClicked: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onTroubleWithAppClicked()
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_warning_triangle),
                contentDescription = null,
                Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.TroubleWithTheApp),
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun IdeasSettings(onIdeaClicked: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onIdeaClicked() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lightbulb),
                contentDescription = null,
                Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = stringResource(id = R.string.AnyGoodIdeas),
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun ShareTheAppSettings() {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "https://northFlurry.com/Emre"
                    ) //!!! actual URL of my app will be added after publishing
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.padding(start = 4.dp),
                painter = painterResource(id = R.drawable.ic_world),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.ShareTheApp),
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun RateUsSettings() {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                val appURL =
                    "https://play.google.com/store/apps" //!!! actual URL of my app will be added after publishing
                val playIntent: Intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(appURL)
                }
                try {
                    context.startActivity(playIntent)
                } catch (e: Exception) {
                    Log.e("TAG", "Error opening URL: ${e.message}")
                }
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hand_heart),
                contentDescription = null,
                Modifier.padding(top = 4.dp, start = 4.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.RateUs),
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun LottieFilesAndTerms(onLottieClicked: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onLottieClicked()
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_attribution_24),
                contentDescription = null,
                Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.LottieFilesAndTerms),
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}