package com.example.wouple.activities.firstScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R

@Preview(showBackground = true)
@Composable
fun PreviewFirstScreenView() {
    FirstScreenView(
        onStartButtonClicked = { /* No action for preview */ }
    )
}

@Composable
fun FirstScreenView(
    onStartButtonClicked: () -> Unit
) {
    val currentContext = LocalContext.current
    val darkBackground = listOf(
        Color(0xFF1D244D),
        Color(0xFF2E3A59),
        Color(0xFF3F5066),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.icondrop),
            contentDescription = "logo",
            alignment = Center,
            modifier = Modifier.size(150.dp)
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.WeatherForecast),
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.SansSerif,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.AppExplainer),
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Button(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ),
            onClick = {
                onStartButtonClicked()
            }
        ) {
            Text(
                text = stringResource(id = R.string.GetStartButton)
            )
        }
        Spacer(modifier = Modifier.weight(1.5f))
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    val url = "https://sites.google.com/view/northflurry/home"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
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