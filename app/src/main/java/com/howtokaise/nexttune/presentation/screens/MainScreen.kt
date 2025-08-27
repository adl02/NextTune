package com.howtokaise.nexttune.presentation.screens

import AnimatedGlowingBackground
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.R
import com.howtokaise.nexttune.domain.data.ChatMessage
import com.howtokaise.nexttune.domain.navigation.Route
import com.howtokaise.nexttune.presentation.RoomViewmodel
import com.howtokaise.nexttune.presentation.YouTubePlayerComposable

@Composable
fun MainScreen(navHostController: NavHostController,viewmodel: RoomViewmodel) {

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
                    count = 3,
                    onClick = {
                        navHostController.navigate(Route.Participants.route)
                    }
                )

                Spacer(modifier = Modifier.width(19.dp))

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                YouTubePlayerComposable(videoId = "C5AGwYeItUk")//k
                LiveChat(viewmodel)
            }

        }
    }
}