package com.example.wouple.activities.startScreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.GetPulsatingEffect
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.SnowfallEffect
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.mocassin
import com.example.wouple.ui.theme.vintage
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    val locations = listOf(
        SearchedLocation("59.9127", "10.7461", "Oslo"),
        SearchedLocation("36.377", "33.9344", "Silifke"),
    )
    val searchedLocation = remember { mutableStateOf<SearchedLocation?>(null) }
    StartScreenView(
        locations = locations,
        onSearch = { },
        onButtonClicked = { },
        searchedLocation = searchedLocation
    )
}

@Composable
fun StartScreenView(
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    onButtonClicked: (SearchedLocation) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>
) {
    val searchBarVisible = remember { mutableStateOf(false) }
    val colors = listOf(
        Color(0xFF3D52BB),
        Color(0xFF3D52BB)
    )
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = colors
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(40.dp))
            AnimatedVisibility(
               visible = searchBarVisible.value,
                enter = fadeIn(
                    //initialOffsetY = { -40 },
                    animationSpec = tween(durationMillis = 1000, easing = EaseIn)
                ) + fadeIn(initialAlpha = 0.3f),
            ) {
                GetPulsatingEffect {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.SearchBeforeYouGo),
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                        fontSize = 16.sp,
                        color = vintage,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(2.dp))
            AnimatedVisibility(
                visible = searchBarVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(durationMillis = 2000, easing = EaseIn)
                ) + expandVertically(animationSpec = tween(durationMillis = 1000))
            ) {
                SimpleSearchBar(onSearch)
            }
            if (locations != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(locations) { location ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 16.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .clickable {
                                    searchedLocation.value = location
                                    onButtonClicked(location)
                                },
                            elevation = 4.dp,
                            backgroundColor = White
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(id = R.drawable.ic_pin),
                                    contentDescription = null,
                                    tint = Unspecified
                                )
                                Text(
                                    text = location.display_name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
        SnowfallEffect(searchBarVisible)
    }
   GetAnimationOfWelcome()
   GetHorizontalWaveForStartPage()
}

@Composable
fun SimpleSearchBar(
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = listOf(White, Color(0xFF4067DD)) //Color(0xFF56CCF2))
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
            .padding(horizontal = 24.dp)
            .height(55.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            value = query,
            maxLines = 1,
            onValueChange = {
                query = it
                onSearch(it)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 18.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
            ),
            placeholder = {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = stringResource(id = R.string.SearchACity),
                    color = Color.Black.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                capitalization = KeyboardCapitalization.Characters,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Spiro,
            )
        )
    }
}
@Composable
private fun GetHorizontalWaveForStartPage(){
    Box(
        modifier = Modifier.fillMaxSize(),

        contentAlignment = BottomCenter
    ) {
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 0f),
            alpha = 1f,
            amplitude = 50f,
            frequency = 0.5f,
            gradientColors = listOf(mocassin, White)
        )
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 15f),
            alpha = 0.5f,
            amplitude = 80f,
            frequency = 0.4f,
            gradientColors = listOf(mocassin, White)
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.2f,
            amplitude = 60f,
            frequency = 0.4f,
            gradientColors = listOf(mocassin, White)
        )
    }
}

@Composable
private fun GetAnimationOfWelcome(){
    var cardsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Float) {
        delay(400)
        cardsVisible = true
    }
    LaunchedEffect(cardsVisible) {
        if (cardsVisible) {
            delay(3000)
            cardsVisible = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        AnimatedVisibility(
            visible = cardsVisible,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = LinearEasing
                )
            ),
            exit = fadeOut(
                animationSpec = tween(2000, easing = EaseOut)
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.ThankYouForDownloading),
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}
