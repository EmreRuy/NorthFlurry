package com.example.wouple.activities.startScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
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
        // Color(0xFF3D52BB)
    ) {
        Image(
            painter = painterResource(id = R.drawable.backgroynd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = FillBounds // Adjust contentScale as needed
        )
        Spacer(modifier = Modifier.height(if (searchBarVisible.value) 100.dp else 0.dp))
       // getImageLogo()
        GetIconOfMan()
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
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
private fun getImageLogo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        contentAlignment = Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
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
            animationSpec = tween(durationMillis = 2000, easing = EaseIn)
        ) + expandVertically(animationSpec = tween(durationMillis = 1000))
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
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = listOf(White, Color(0xFF4067DD)) //Color(0xFF56CCF2))
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
private fun GetHorizontalWaveForStartPage() {
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
