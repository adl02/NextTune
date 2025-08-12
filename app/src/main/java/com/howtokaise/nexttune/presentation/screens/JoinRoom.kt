package com.howtokaise.nexttune.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.domain.navigation.Route
import com.howtokaise.nexttune.presentation.RoomViewmodel

@Composable
fun JoinRoom(navHostController: NavHostController,viewmodel: RoomViewmodel) {

    val roomData by viewmodel.roomData.collectAsState()
    val status by viewmodel.status.collectAsState()
    val navigationToMain by viewmodel.navigateToMain.collectAsState()

    var joinuser by remember { mutableStateOf("") }
    var roomCode by remember { mutableStateOf("") }

    var JoinUsernameError by remember { mutableStateOf(false) }
    var JoinRoomCodeError by remember { mutableStateOf(false) }

    val JoinuserValid = joinuser.isNotBlank()
    val roomCodeValid = roomCode.isNotBlank()

    LaunchedEffect(status) {
        if (status == "room-created" || status == "user-joined") {
            navHostController.navigate(Route.MainScreen.route) {
                popUpTo(Route.HomeScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1F2836))

    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Join Room",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = joinuser,
                onValueChange = {
                    joinuser = it
                    if (joinuser.isNotBlank()) JoinUsernameError = false
                },
                placeholder = {
                    if (JoinUsernameError) {
                        Text("Please enter your name", color = Color.Red)
                    } else {
                        Text("Enter your name", color = Color.Gray)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0XFF2A3444),
                    unfocusedContainerColor = Color(0XFF2A3444),
                    focusedIndicatorColor = if (JoinUsernameError) Color.Red else Color(
                        0xFFB246F8
                    ),
                    unfocusedLabelColor = if (JoinUsernameError) Color.Red else Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = roomCode,
                onValueChange = {
                    roomCode = it
                    if (roomCode.isNotBlank()) JoinRoomCodeError = false
                },
                placeholder = {
                    if (JoinRoomCodeError) {
                        Text("Please enter your 6-digit code", color = Color.Red)
                    } else {
                        Text(
                            "Enter 6-digit code",
                            fontSize = 18.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0XFF2A3444),
                    unfocusedContainerColor = Color(0XFF2A3444),
                    focusedIndicatorColor = if (JoinRoomCodeError) Color.Red else Color(
                        0xFFB246F8
                    ),
                    unfocusedLabelColor = if (JoinRoomCodeError) Color.Red else Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    JoinUsernameError = !JoinuserValid
                    JoinRoomCodeError = !roomCodeValid

                    if (JoinuserValid && roomCodeValid){
                        viewmodel.joinRoom(
                            name = joinuser,
                            roomCode = roomCode
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFB246F8),
                                Color(0xFFFF00CC)
                            )
                        )
                    )
            ) {
                Text("Join Room", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}