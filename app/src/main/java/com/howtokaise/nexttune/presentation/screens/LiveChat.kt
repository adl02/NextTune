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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.howtokaise.nexttune.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LiveChat() {

    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF242C3B))
    ) {

        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(Color(0xFF242E38)),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Live Chat",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ){

            item {
                Row(
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = "NextTune", color = Color.White, fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "16:00", fontSize = 11.sp, color = Color.LightGray
                            )
                        }
                        Text(
                            text = "Welcome to the room, Invite and enjoy with your loved ones!",
                            color = Color.LightGray
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = "NextTune", color = Color.White, fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "16:00", fontSize = 11.sp, color = Color.LightGray
                            )
                        }
                        Text("Welcome to the room, Invite and enjoy with your loved ones!", color = Color.LightGray)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = "NextTune", color = Color.White, fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "16:00", fontSize = 11.sp, color = Color.LightGray
                            )
                        }
                        Text("Welcome to the room, Invite and enjoy with your loved ones!", color = Color.LightGray)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = "NextTune", color = Color.White, fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "16:00", fontSize = 11.sp, color = Color.LightGray
                            )
                        }
                        Text("Welcome to the room, Invite and enjoy with your loved ones!", color = Color.LightGray)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = "NextTune", color = Color.White, fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "16:00", fontSize = 11.sp, color = Color.LightGray
                            )
                        }
                        Text("Welcome to the room, Invite and enjoy with your loved ones!", color = Color.LightGray)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 22.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = "NextTune", color = Color.White, fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "16:00", fontSize = 11.sp, color = Color.LightGray
                            )
                        }
                        Text("Welcome to the room, Invite and enjoy with your loved ones!", color = Color.LightGray)
                    }
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF3E444B)),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                    //.background(Color(0xFF1B2735)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder ={ Text(text = "Type a message", color = Color.Gray)},
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                       // .background(Color(0xFF3E444B)),
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
                 // Spacer(modifier = Modifier.width(8.dp))
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
                        .clickable {
                            // Handle send click
                        },
                    contentAlignment = Alignment.Center,
                ){
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