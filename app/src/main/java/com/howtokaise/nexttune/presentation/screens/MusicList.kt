package com.howtokaise.nexttune.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.howtokaise.nexttune.domain.data.VideoItem
import com.howtokaise.nexttune.domain.data.YouTubeSearchResponse
import com.howtokaise.nexttune.presentation.viewmodel.RoomViewmodel
import com.howtokaise.nexttune.presentation.viewmodel.YouTubeViewModel


@Composable
fun MusicList(
    roomViewModel: RoomViewmodel,
    youtubeViewModel: YouTubeViewModel,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }
    val searchResults by youtubeViewModel.results.collectAsState()
    val isLoading by youtubeViewModel.isLoading.collectAsState()
    val errorMessage by youtubeViewModel.errorMessage.collectAsState()
    val videoQueue by roomViewModel.videoQueue.collectAsState()
    val isAdmin by roomViewModel.isAdmin.collectAsState()

    Column(
        modifier = modifier
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
        // Header
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF0072FF).copy(alpha = 0.5f),
                            Color(0xFF242C3B).copy(alpha = 0.2f)
                        )
                    )
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color(0xFF00C6FF)
            )
            Text(
                text = if (isSearching) "Search Videos" else "Up Next",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isAdmin) {
                Box(
                    modifier = Modifier.clickable {
                        isSearching = !isSearching
                        if (!isSearching) {
                            youtubeViewModel.clearResults()
                            searchText = ""
                        }
                    }
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFF00C6FF)
                        )
                        Text(
                            text = if (isSearching) "Done" else "Search",
                            color = Color(0xFF00C6FF),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }

        // Search Section
        if (isSearching && isAdmin) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Search Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search YouTube videos...") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00C6FF),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = Color(0xFF00C6FF),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f)
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (searchText.isNotBlank()) {
                                roomViewModel.searchAndAddVideo(searchText, youtubeViewModel)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C6FF)
                        )
                    ) {
                        Text("Search")
                    }
                }

                // Loading and Error States
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF00C6FF))
                    }
                }

                errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Search Results
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(searchResults) { result ->
                        SearchResultItem(
                            result = result,
                            onAddToQueue = {
                                val videoItem = VideoItem(
                                    videoId = result.id.videoId,
                                    title = result.snippet.title,
                                    thumbnailUrl = result.snippet.thumbnails.medium.url
                                )
                                roomViewModel.addVideoToQueue(videoItem)
                            }
                        )
                    }
                }
            }
        } else {
            // Queue List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                if (videoQueue.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(48.dp)
                                )
                                Text(
                                    text = "No videos in queue",
                                    color = Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                } else {
                    items(videoQueue) { video ->
                        QueueVideoItem(
                            video = video
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    result: YouTubeSearchResponse,
    onAddToQueue: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Image(
                painter = rememberAsyncImagePainter(result.snippet.thumbnails.medium.url),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Video Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = result.snippet.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Add Button
            IconButton(
                onClick = onAddToQueue,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to queue",
                    tint = Color(0xFF00C6FF)
                )
            }
        }
    }
}

@Composable
fun QueueVideoItem(
    video: VideoItem
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Image(
                painter = rememberAsyncImagePainter(video.thumbnailUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Video Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = video.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}