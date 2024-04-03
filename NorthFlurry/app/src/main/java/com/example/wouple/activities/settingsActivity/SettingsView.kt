package com.example.wouple.activities.settingsActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.UnitSettings
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.model.api.WindUnit
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.preferences.WindUnitPref
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.beige
import com.example.wouple.ui.theme.getSecondaryGradients
import com.example.wouple.ui.theme.orgn
import kotlinx.coroutines.delay


/*@Preview
@Composable
fun SettingsPreview() {
    TemperatureUnitSettings()
}*/


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsView(
    onBackPressed: () -> Unit,
    onFeedbackClicked: (Boolean) -> Unit,
    temp: TemperatureResponse
) {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }
    var cardsVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(600)
        cardsVisible = true
    }
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        listOf(
            Color(0xFF3954C4),
            Color(0xFF384BB4)
        )
    } else {
        listOf(
            Color(0xFF1C2249),
            Color(0xFF1C2249),
            //Color(0xFF1D244D),
            //Color(0xFF2E3A59)
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(id = R.string.Settings),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = Whitehis,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                contentColor = Whitehis,
                backgroundColor = Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.verticalGradient(background))
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val isDay = temp.current_weather.is_day == 1
                val background: List<Color> = if (isDay) {
                    val baseColor = Color(0xFF3F54BE)//Color(0xFF4067DD)
                    val lighterShades = listOf(
                        baseColor,
                        baseColor.copy(alpha = 0.9f),
                        baseColor.copy(alpha = 0.8f),
                        baseColor.copy(alpha = 0.5f),
                    )

                    lighterShades
                } else {
                    listOf(
                        Color(0xFF1D244D),
                        Color(0xFF2E3A59),
                        Color(0xFF3F5066),
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .background(brush = Brush.verticalGradient(background)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 32.dp))
                    CustomTab(
                        selectedItemIndex = selected,
                        items = listOf("General", "Units"),
                        onClick = {
                            setSelected(it)
                        }
                    )
                    when (selected) {
                        0 -> {
                            AnimatedVisibility(
                                visible = cardsVisible,
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 2000,
                                        easing = LinearEasing
                                    )
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    //LanguageSettings()
                                    SettingsCardOne()
                                    TroubleOnAppSettings { onFeedbackClicked(true) }
                                    IdeasSettings { onFeedbackClicked(false) }
                                    ShareTheAppSettings()
                                    RateUsSettings()
                                }
                            }
                        }

                        1 -> {
                            SettingsCardTwo()
                            TemperatureUnitSettings(temp)
                            PrecipitationUnitSettings(temp)
                            WindUnitSettings(temp)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = BottomCenter
                ) {
                    HorizontalWave(
                        phase = rememberPhaseState(startPosition = 0f),
                        alpha = 0.5f,
                        amplitude = 40f,
                        frequency = 0.5f,
                        gradientColors = listOf(White, White)
                    )
                    HorizontalWave(
                        phase = rememberPhaseState(startPosition = 15f),
                        alpha = 0.5f,
                        amplitude = 80f,
                        frequency = 0.4f,
                        gradientColors = listOf(White, White)
                    )
                    HorizontalWave(
                        phase = rememberPhaseState(10f),
                        alpha = 0.2f,
                        amplitude = 60f,
                        frequency = 0.4f,
                        gradientColors = listOf(White, White)
                    )
                }
            }
        }
    )
}

@Composable
private fun LanguageSettings() {
    Spacer(modifier = Modifier.padding(top = 12.dp))
    Text(
        text = stringResource(id = R.string.GeneralSettings),
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Medium,
        color = beige.copy(alpha = 0.8f),
        fontSize = 28.sp
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        val backgroundColor = getSecondaryGradients()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColor))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterStart),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.menuicon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(CenterVertically)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Languages",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(8f))
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "English",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(CenterVertically)
                )
            }
        }
    }
}
@Composable
private fun SettingsCardOne(){
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
private fun SettingsCardTwo() {
    Spacer(modifier = Modifier.padding(top = 12.dp))
    Text(
        text = stringResource(id = R.string.UnitSettings),
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Medium,
        color = beige.copy(alpha = 0.8f),
        fontSize = 28.sp
    )
}

@Composable
fun TemperatureUnitSettings(temp: TemperatureResponse) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.TemperatureUnits),
            color = orgn,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
        val temperatureUnits = TemperatureUnit.values()
        var selectedUnitIndex by remember {
            mutableStateOf(
                temperatureUnits.indexOf(
                    TemperatureUnitPref.getTemperatureUnit(context)
                )
            )
        }
        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                TemperatureUnitPref.setTemperatureUnit(context, temperatureUnits[index])
                selectedUnitIndex = index
            },
            units = temperatureUnits.map { it.toString() },
            temp = temp
        )
    }
}

@Composable
fun WindUnitSettings(temp: TemperatureResponse) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.WindSpeedUnits),
            color = orgn,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
        val units = WindUnit.values()
        var selectedUnitIndex by remember {
            mutableStateOf(
                units.indexOf(
                    WindUnitPref.getWindUnit(
                        context
                    )
                )
            )
        }
        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                WindUnitPref.setWindUnit(context, units[index])
                selectedUnitIndex = index
            },
            units = units.map { it.toString() },
            temp = temp
        )
    }
}

@Composable
fun PrecipitationUnitSettings(temp: TemperatureResponse) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = stringResource(id = R.string.PrecipitationUnits),
            color = orgn,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
        val units = PrecipitationUnit.values()
        var selectedUnitIndex by remember {
            mutableStateOf(
                units.indexOf(
                    PrecipitationUnitPref.getPrecipitationUnit(
                        context
                    )
                )
            )
        }
        UnitSettings(
            selectedUnitIndex = selectedUnitIndex,
            onUnitSelected = { index ->
                PrecipitationUnitPref.setPrecipitationUnit(context, units[index])
                selectedUnitIndex = index
            },
            units = units.map { it.toString() },
            temp = temp
        )
    }
}

@Composable
private fun ShareTheAppSettings() {
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
                    ) //actual URL of my app will be added after publishing
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        val backgroundColor = getSecondaryGradients()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColor))
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.padding(start = 4.dp),
                painter = painterResource(id = R.drawable.ic_world),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(26.dp))
            Text(
                text = stringResource(id = R.string.ShareTheApp),
                fontWeight = FontWeight.SemiBold,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(CenterVertically)
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
                    "https://play.google.com/store/apps" // actual URL of my app will be added after publishing
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
        val backgroundColor = getSecondaryGradients()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColor))
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_hand_heart), contentDescription = null, Modifier.padding(top = 4.dp, start = 4.dp))
            Spacer(modifier = Modifier.width(26.dp))
            Text(
                text = stringResource(id = R.string.RateUs),
                fontWeight = FontWeight.SemiBold,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(CenterVertically)
            )
        }
    }
}

@Composable
private fun TroubleOnAppSettings(onTroubleWithAppClicked: () -> Unit) {
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
        val backgroundColor = getSecondaryGradients()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColor))
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_warning_triangle), contentDescription = null, Modifier.padding(start = 4.dp))
            Spacer(modifier = Modifier.width(26.dp))
            Text(
                text = stringResource(id = R.string.TroubleWithTheApp),
                fontWeight = FontWeight.SemiBold,
                color = Dark20,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(CenterVertically)
            )
        }
    }
}

@Composable
private fun IdeasSettings(onIdeaClicked: () -> Unit) {
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
        val backgroundColor = getSecondaryGradients()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColor))
                .padding(12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_lightbulb), contentDescription = null, Modifier.padding(start = 4.dp))
            Spacer(modifier = Modifier.width(26.dp))
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
                    .align(CenterVertically)
            )
        }
    }
}

@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            ),
    )
}

@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            beige
        } else {
            Black
        },
        animationSpec = tween(easing = LinearEasing), label = "",
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 120.dp,
    //tabHeight: Dp = 50.dp,
    onClick: (index: Int) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing), label = "",
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(White)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor =  Color(0xFF384BB4)//MaterialTheme.colorScheme.primary,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.forEachIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    isSelected = isSelected,
                    onClick = {
                        onClick(index)
                    },
                    tabWidth = tabWidth,
                    text = text,
                )
            }
        }
    }
}
