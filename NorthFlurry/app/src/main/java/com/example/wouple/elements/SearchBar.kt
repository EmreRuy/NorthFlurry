package com.example.wouple.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.ui.theme.Spiro

@Composable
fun SearchBar(
    isSearchExpanded: MutableState<Boolean>,
    onSearch: (String) -> Unit,
    onClose: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = if (isSearchExpanded.value) listOf(
            Color.White,
            Color(0xFF4067DD)
        )//Color(0xFF56CCF2))
        else listOf(Color.Transparent, Color.Transparent)
    )

    LaunchedEffect(isSearchExpanded.value) {
        query = ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AnimatedVisibility(
            visible = isSearchExpanded.value,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            TextField(
                value = query,
                maxLines = 1,
                onValueChange = {
                    query = it
                    if (query.length >= 3) {
                        onSearch(query)
                    }
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
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Search a city or airport",
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus(true)
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

        IconButton(
            onClick = {
                isSearchExpanded.value = !isSearchExpanded.value
                if (!isSearchExpanded.value) {
                    query = ""
                }
                onClose()
            },
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    bottom = if (isSearchExpanded.value) 0.dp else 8.dp
                )
                .rotate(if (isSearchExpanded.value) 1f else 360f)
        ) {
            Icon(
                imageVector = if (isSearchExpanded.value) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = "Search",
                tint = if (isSearchExpanded.value) Color.Black else Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
