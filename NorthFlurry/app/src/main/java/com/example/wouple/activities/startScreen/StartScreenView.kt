package com.example.wouple.activities.startScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
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
import com.example.wouple.activities.ui.theme.getBackgroundGradient
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.mocassin
import com.example.wouple.ui.theme.vintage
import kotlinx.coroutines.delay
import kotlin.random.Random

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
    var searchBarVisible by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(800)
        textVisible = true
    }
    LaunchedEffect(Unit) {
        delay(1500)
        searchBarVisible = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = getBackgroundGradient()
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SnowfallEffect()
        // Navigation()
        Spacer(modifier = Modifier.padding(40.dp))
        /*   val scale by animateFloatAsState(
               targetValue = if (textVisible) 1f else 0.8f,
               animationSpec = infiniteRepeatable(
                   animation = tween(durationMillis = 500),
                   repeatMode = RepeatMode.Reverse
               )
           ) */
        AnimatedVisibility(
            visible = textVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(durationMillis = 1000, easing = EaseOut)
            ) + fadeIn(initialAlpha = 0.3f)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                text = "Sun, Rain, or Snow – Know Before You Go!",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                color = vintage,
            )
        }
        Spacer(modifier = Modifier.padding(6.dp))
        AnimatedVisibility(
            visible = searchBarVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(durationMillis = 1000, easing = EaseIn)
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
                            .padding(vertical = 4.dp, horizontal = 24.dp)
                            .clip(RoundedCornerShape(24.dp))
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
                                painter = painterResource(id = R.drawable.pin),
                                contentDescription = null,
                                tint = Color.Unspecified
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
        // .padding(bottom = 38.dp),
        contentAlignment = Center
    ) {
        AnimatedVisibility(
            visible = cardsVisible,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = LinearEasing
                )
            )
        ) {
            Row {
                Icon(painter = painterResource(id = R.drawable.sun), contentDescription = null)
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Thank you for downloading NorthFlurry, your ultimate weather companion!",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }

        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 0f),
            alpha = 1f,
            amplitude = 50f,
            frequency = 0.5f,
            gradientColors = listOf(mocassin, Color(0xFF56CCF2))
        )
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 15f),
            alpha = 0.5f,
            amplitude = 80f,
            frequency = 0.4f,
            gradientColors = listOf(Color(0xFF2F80ED), Color(0xFF56CCF2))
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.2f,
            amplitude = 60f,
            frequency = 0.4f,
            gradientColors = listOf(Color(0xFF2F80ED), Color(0xFF56CCF2))
        )
    }
}

@Composable
fun SimpleSearchBar(
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = listOf(White, Color(0xFF56CCF2))
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
                    text = "Search a city or airport",
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
data class Snowflake(
    var x: Float,
    var y: Float,
    var radius: Float,
    var speed: Float
)

@Composable
fun SnowfallEffect() {
    val snowflakes = remember { List(100) { generateRandomSnowflake() } }

    val offsetY = remember { Animatable(0f) }
    val colorAlpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 1000f,
            animationSpec = tween(durationMillis = 5_000, easing = LinearEasing)
        )
        colorAlpha.animateTo(
            0f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
        )
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Transparent)) {
        snowflakes.forEach { snowflake ->
            drawSnowflake(colorAlpha.value, snowflake, offsetY.value % size.height)
        }
    }
}

fun generateRandomSnowflake(): Snowflake {
    return Snowflake(
        x = Random.nextFloat(),
        y = Random.nextFloat() * 1000f,
        radius = Random.nextFloat() * 2f + 2f, // Snowflake size
        speed = Random.nextFloat() * 1.2f + 1f  // Falling speed
    )
}

fun DrawScope.drawSnowflake(alpha: Float, snowflake: Snowflake, offsetY: Float) {
    val newY = (snowflake.y + offsetY * snowflake.speed) % size.height
    drawCircle(White, alpha = alpha, radius = snowflake.radius, center = Offset(snowflake.x * size.width, newY))
}