package com.howtokaise.nexttune.presentation.screens

import AnimatedGlowingBackground
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.howtokaise.nexttune.domain.data.chat
import com.howtokaise.nexttune.presentation.RoomViewmodel
import kotlinx.coroutines.delay



@Composable
fun LiveChat(viewmodel: RoomViewmodel) {

    var message by remember { mutableStateOf("") }
    val messages = viewmodel.messages
    AnimatedGlowingBackground {
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
                Text(
                    text = "Live Chat",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                TypingDotsAnimation()
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {

                items(messages) { chatItem ->
                    Row(
                        modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
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

                                Text(
                                    text = chatItem.time, fontSize = 11.sp, color = Color.LightGray
                                )
                            }
                            Text(
                                text = chatItem.message,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00C6FF).copy(alpha = 0.2f)),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text(text = "Type a message", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                        )
                    )

                    Box(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(width = 55.dp, height = 45.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF00C6FF), Color(0xFF0072FF))
                                )
                            )
                            .clickable { },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TypingDotsAnimation() {
    val dotCount = 3
    val delays = listOf(0L, 250L, 500L)

    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        repeat(dotCount) { index ->
            val yOffset = remember { Animatable(0f) }

            LaunchedEffect(key1 = index) {
                while (true) {
                    delay(delays[index])
                    yOffset.animateTo(
                        targetValue = -6f,
                        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                    )
                    yOffset.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                    )
                    delay(300)
                }
            }

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .offset(y = yOffset.value.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}