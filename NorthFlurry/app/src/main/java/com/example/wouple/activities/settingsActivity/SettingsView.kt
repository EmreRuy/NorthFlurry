package com.example.wouple.activities.settingsActivity

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.ui.theme.getBackgroundGradient
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.beige
import com.example.wouple.ui.theme.clearSky

/*@Preview
@Composable
fun SettingsPreview() {
    WoupleTheme {
        SettingsView()*/


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsView(
    onBackPressed: () -> Unit,
    onFeedbackClicked: (Boolean) -> Unit,
) {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "Settings",
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
                backgroundColor = clearSky
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .background(brush = Brush.verticalGradient(getBackgroundGradient())),
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
                            ExpandableCard()
                            SettingsCardOne()
                            SettingsCardFive { onFeedbackClicked(true) }
                            SettingsCardSix { onFeedbackClicked(false) }
                        }

                        1 -> {
                            SettingsCardTwo()
                            SettingsCardThree()
                            TroubleWithApp()
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
                        gradientColors = listOf(Color(0xFF608ECA), Color(0xFF56CCF2))
                    )
                    HorizontalWave(
                        phase = rememberPhaseState(startPosition = 15f),
                        alpha = 0.5f,
                        amplitude = 80f,
                        frequency = 0.4f,
                        gradientColors = listOf(Color(0xFF608ECA), Color(0xFF56CCF2))
                    )
                    HorizontalWave(
                        phase = rememberPhaseState(10f),
                        alpha = 0.2f,
                        amplitude = 60f,
                        frequency = 0.4f,
                        gradientColors = listOf(Color(0xFF608ECA), Color(0xFF56CCF2))
                    )
                }
            }
        }
    )
}

@Composable
private fun SettingsCardOne() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        shape = RoundedCornerShape(26.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterStart)
            ) {
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
private fun DarkModeSwitch() {
    var checked by remember { mutableStateOf(false) }
    Switch(
        checked = checked,
        onCheckedChange = { checked = it },
        modifier = Modifier.padding(4.dp),
        colors = SwitchDefaults.colors(
            checkedThumbColor = Spiro,
            checkedTrackColor = Spiro,
            uncheckedThumbColor = beige,
            uncheckedTrackColor = Color.Gray,
        ),

        )
}

@Composable
private fun SettingsCardTwo() {
    Spacer(modifier = Modifier.padding(top = 8.dp))
    Text(
        text = "Unit Settings",
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Medium,
        color = beige.copy(alpha = 0.8f),
        fontSize = 28.sp
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        shape = RoundedCornerShape(26.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 12.dp),
                    text = "Dark Mode",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                DarkModeSwitch()
            }
        }
    }
}

@Composable
private fun SettingsCardThree() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Help & Send Feedback",
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
}

@Composable
fun ExpandableCard() {
    var expanded by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.padding(top = 8.dp))
    Text(
        text = "General Settings",
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Medium,
        color = beige.copy(alpha = 0.8f),
        fontSize = 28.sp
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = 70.dp,
                max = if (expanded) 150.dp else 70.dp
            ) // Adjust max height as needed
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .clickable {
                expanded = !expanded
            }
    ) {
        Column {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = "You can customize your weather preferences here",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
private fun SettingsCardFive(onTroubleWithAppClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTroubleWithAppClicked() }
            .padding(horizontal = 18.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Trouble with the app?",
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
            )
        }
    }
}


@Composable
private fun SettingsCardSix(onIdeaClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onIdeaClicked() }
            .padding(horizontal = 18.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Any good ideas?",
                fontWeight = FontWeight.Medium,
                color = Dark20,
                fontSize = 18.sp
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
    )
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 100.dp,
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
            indicatorColor = MaterialTheme.colorScheme.primary,
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

@Composable
fun TroubleWithApp() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(painter = painterResource(id = R.drawable.sun), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Trouble with the App?",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp
                )
            }
        }
    }
}