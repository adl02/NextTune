package com.howtokaise.nexttune.presentation.screens

import AnimatedGlowingBackground
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.R
import com.howtokaise.nexttune.domain.navigation.Route
import com.howtokaise.nexttune.presentation.viewmodel.RoomViewmodel
import com.howtokaise.nexttune.presentation.viewmodel.YouTubeViewModel
import com.howtokaise.nexttune.presentation.youtube.YouTubePlayerComposable
import com.howtokaise.nexttune.presentation.youtube.YouTubeThumbnail

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MainScreen(navHostController: NavHostController, viewmodel: RoomViewmodel) {

    val youtubeViewModel: YouTubeViewModel = viewModel()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val participant by viewmodel.participants.collectAsState()
    val isSettingsOpen by viewmodel.isSettingsOpen.collectAsState()
    val currentVideoId by viewmodel.currentVideoId.collectAsState()

    if (isSettingsOpen) {
        SettingScreen(viewmodel = viewmodel, navController = navHostController)
    }

    AnimatedGlowingBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .background(Color(0XFF121926))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.appicon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RectangleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "NextTune",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00C6FF),
                                Color(0xFF0072FF)
                            )
                        )
                    ),
                    fontSize = 23.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                ParticipantCount(
                    count = participant.size,
                    onClick = {
                        navHostController.navigate(Route.Participants.route)
                    }
                )

                Spacer(modifier = Modifier.width(19.dp))

                IconButton(
                    onClick = {
                        if (isSettingsOpen) {
                            viewmodel.closeSettings()
                        } else {
                            viewmodel.openSettings()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isSettingsOpen) Icons.Default.Close else Icons.Default.Menu,
                        contentDescription = if (isSettingsOpen) "Close Settings" else "Open Settings",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
               // YouTubePlayerComposable(videoId = currentVideoId) // youtube new policy
                YouTubeThumbnail(imageId = "TBxS0XhdfmU")

                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                    when (page) {
                        0 -> LiveChat(viewmodel)
                        1 -> MusicList(
                            roomViewModel = viewmodel,
                            youtubeViewModel = youtubeViewModel,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}