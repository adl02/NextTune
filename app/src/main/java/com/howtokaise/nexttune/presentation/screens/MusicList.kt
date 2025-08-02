package com.howtokaise.nexttune.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.howtokaise.nexttune.presentation.YouTubeViewModel


@OptIn(ExperimentalMaterial3Api::class)
// @Preview(showBackground = true, showSystemUi = true)
@Composable
fun MusicList(viewModel: YouTubeViewModel) {

    var searchText by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF242C3B).copy(alpha = 0.8f),
                        Color(0xFF0072FF).copy(alpha = 0.1f)
                    )
                )
            )
    ) {

        if (isSearching){
            SearchBar(
                query = searchText,
                onQueryChange = {searchText = it},
                onSearch = {
                    viewModel.search(searchText)
                    isSearching = false
                },
                active = isSearching,
                onActiveChange = { isSearching = it},
                placeholder = { Text("Search your music here") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.Transparent)
            ) { }
        } else {


            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF0072FF).copy(alpha = 0.5f),
                                Color(0xFF242C3B0).copy(alpha = 0.2f)
                            )
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFF00C6FF)
                )
                Text(
                    text = "Up Next",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { isSearching = true }
                    ) {
                        Row(

                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color(0xFF00C6FF)
                            )
                            Text(
                                text = "Search",
                                color = Color(0xFF00C6FF),
                            )
                        }
                    }
                }

            }
        }
    }
}