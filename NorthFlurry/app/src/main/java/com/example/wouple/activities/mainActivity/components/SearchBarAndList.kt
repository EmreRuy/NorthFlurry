package com.example.wouple.activities.mainActivity.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.SearchBar
import com.example.wouple.model.api.SearchedLocation
import kotlinx.coroutines.launch

@Composable
fun GetSearchBarAndList(
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onClose: () -> Unit,
) {
    val isSearchExpanded = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(
         modifier = Modifier
             .padding(4.dp)
             .fillMaxWidth()
     ) {
         SearchBar(isSearchExpanded, onSearch, onClose)
         if (locations != null) {
             LazyColumn(
                 state = listState,
                 modifier = Modifier
                     .heightIn(max = 300.dp)
                     .padding(vertical = 4.dp),
                 contentPadding = PaddingValues(vertical = 4.dp)
             ) {
                 items(locations) { location ->
                     Card(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(vertical = 4.dp, horizontal = 16.dp)
                             .clip(RoundedCornerShape(30.dp))
                             .clickable {
                                 isSearchExpanded.value = false
                                 searchedLocation.value = location
                                 onLocationButtonClicked(location)
                                 coroutineScope.launch {
                                     listState.animateScrollToItem(0)
                                 }
                             },
                         elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                         colors = CardDefaults.cardColors(
                             containerColor = Color.White,
                         )
                     ) {
                         Row(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(12.dp),
                             verticalAlignment = Alignment.CenterVertically
                         ) {
                             Icon(
                                 painter = painterResource(id = R.drawable.ic_pin),
                                 contentDescription = null,
                                 tint = Color.Unspecified,
                                 modifier = Modifier.size(28.dp)
                             )
                             Spacer(modifier = Modifier.width(12.dp))
                             Text(
                                 text = location.display_name,
                                 fontWeight = FontWeight.Bold,
                                 fontSize = 16.sp,
                                 color = Color.Black,
                                 modifier = Modifier
                                     .animateContentSize()
                                     .padding(4.dp)
                             )
                         }
                     }
                 }
             }
         }
     }
}
