package com.example.wouple.activities.detailActivity.components.openMetActivity

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import androidx.core.net.toUri

@Composable
fun OpenMeteorologyView() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(top = 64.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.Attribution),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = stringResource(id = R.string.open_meteo_attribution_part1) + "\n" +
                    stringResource(id = R.string.open_meteo_attribution_part2) + "\n" +
                    stringResource(id = R.string.open_meteo_attribution_part3),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
        Text(
            text = stringResource(id = R.string.open_meteo_visit),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .clickable {
                    val url = "https://open-meteo.com/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = url.toUri()
                    context.startActivity(intent)
                }
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp),
            text = stringResource(id = R.string.open_meteo_licenceInfo),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = stringResource(id = R.string.weather_data_ccBy_licence),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
        LicenseLinkText()
        Text(
            text = stringResource(id = R.string.DataAccuracyDisclaimer),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Text(
            text = stringResource(id = R.string.weather_data_disclaimer),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(16.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
    }
}

@Composable
fun LicenseLinkText() {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        val label = stringResource(id = R.string.cc_by_4_license_details)

        pushStringAnnotation(
            tag = "URL",
            annotation = "https://creativecommons.org/licenses/by/4.0/"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(label)
        }
        pop()
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.clickable {
            annotatedText
                .getStringAnnotations("URL", 0, annotatedText.length)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, annotation.item.toUri())
                    context.startActivity(intent)
                }
        }
    )
}

