package com.example.wouple.activities.startScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.GetPulsatingEffect
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.SnowfallEffect
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.vintage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StartScreenView(
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    onButtonClicked: (SearchedLocation) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>
) {
    val searchBarVisible = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3D52BB))
    ) {
        AnimationOfSearchScreen()
        Spacer(modifier = Modifier.height(if (searchBarVisible.value) 100.dp else 0.dp))
        GetIconOfMan()
        Column(horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {
            SearchSection(
                searchBarVisible = searchBarVisible,
                onSearch = onSearch
            )
            LocationList(
                locations = locations,
                onButtonClicked = onButtonClicked,
                searchedLocation = searchedLocation,
            )
        }
    }
    SnowfallEffect(searchBarVisible)
    GetAnimationOfWelcome()
    GetHorizontalWaveForStartPage()
}

@Composable
private fun AnimationOfSearchScreen() {
    var animationVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Float) {
        delay(5500)
        animationVisible = true
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        AnimatedVisibility(
            visible = animationVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
        ) {
            Text(
                modifier = Modifier.padding(start = 38.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Default,
                fontSize = 28.sp,
                color = vintage,
            )
            Text(
                modifier = Modifier.padding(vertical = 36.dp),
                text = stringResource(id = R.string.WeatherForecast),
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif,
                fontSize = 28.sp,
                color = Corn,
            )
        }
    }
}

@Composable
fun SearchSection(
    searchBarVisible: MutableState<Boolean>,
    onSearch: (String) -> Unit,
) {
    Spacer(modifier = Modifier.padding(top = 40.dp))
    AnimatedVisibility(
        visible = searchBarVisible.value,
        enter = fadeIn(
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
            animationSpec = tween(durationMillis = 1000, easing = EaseIn)
        ) + expandVertically(animationSpec = tween(durationMillis = 800))
    ) {
        SimpleSearchBar(onSearch)
    }
}

@Composable
fun LocationList(
    locations: List<SearchedLocation>?,
    onButtonClicked: (SearchedLocation) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>
) {
    if (locations != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(locations) { location ->
                LocationItem(
                    location = location,
                    onButtonClicked = onButtonClicked,
                    searchedLocation = searchedLocation
                )
            }
        }
    }
}

@Composable
fun LocationItem(
    location: SearchedLocation,
    onButtonClicked: (SearchedLocation) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable {
                searchedLocation.value = location
                onButtonClicked(location)
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
        colors = CardDefaults.cardColors(
            contentColor = White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_pin),
                contentDescription = null,
                tint = Unspecified
            )
            Text(
                text = location.display_name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

@Composable
fun GetIconOfMan() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        contentAlignment = BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_man),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun SimpleSearchBar(
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = listOf(White, Color(0xFF4067DD))
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(brush = gradient, shape = RoundedCornerShape(28.dp))
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
                    text = stringResource(id = R.string.SearchBar),
                    color = Color.Black.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                    focusManager.clearFocus(true)
                }
            ),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Spiro,
            )
        )
    }
}

@Composable
private fun GetHorizontalWaveForStartPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = BottomCenter
    ) {
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 0f),
            alpha = 1f,
            amplitude = 50f,
            frequency = 0.5f
        )
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 15f),
            alpha = 0.5f,
            amplitude = 80f,
            frequency = 0.4f
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.2f,
            amplitude = 60f,
            frequency = 0.4f
        )
    }

}

@Composable
private fun GetAnimationOfWelcome() {
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        AnimatedVisibility(
            visible = cardsVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 2000, easing = LinearEasing))
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.ThankYouForDownloading),
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}
