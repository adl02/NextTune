package com.howtokaise.nexttune.presentation.screens

import AnimatedGlowingBackground
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.R
import com.howtokaise.nexttune.domain.chat
import com.howtokaise.nexttune.domain.peoples

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Participants(navHostController: NavHostController) {
    AnimatedGlowingBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

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
                Image(
                    painter = painterResource(R.drawable.baseline_people_alt_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = "Participants",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                ParticipantCount(
                    count = 3,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            ){
                items(peoples){ chatItem ->
                    Row(
                        modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp, bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = 0.4.dp,
                                    Color.Cyan,
                                    shape = CircleShape
                                )
                                .background(Color.Transparent, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Row {
                                Text(
                                    text = chatItem.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                            }
                            Text(
                                text = chatItem.time,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}