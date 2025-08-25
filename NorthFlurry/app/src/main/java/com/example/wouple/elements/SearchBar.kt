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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R

@Composable
fun SearchBar(
    isSearchExpanded: MutableState<Boolean>,
    onSearch: (String) -> Unit,
    onClose: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

    val gradient = Brush.horizontalGradient(
        colors = if (isSearchExpanded.value) listOf(
            colors.inversePrimary, // Background surface color
            colors.primary // Dynamic primary color
        ) else listOf(Color.Transparent, Color.Transparent)
    )
    LaunchedEffect(isSearchExpanded.value) {
        query = ""
        onSearch("")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(id = R.string.SearchBar),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus(true)
                    }
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
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
                .padding(end = 16.dp, bottom = if (isSearchExpanded.value) 0.dp else 8.dp)
                .rotate(if (isSearchExpanded.value) 1f else 360f)
        ) {
            Icon(
                imageVector = if (isSearchExpanded.value) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
