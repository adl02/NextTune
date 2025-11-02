package com.howtokaise.nexttune.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.domain.navigation.Route
import com.howtokaise.nexttune.presentation.viewmodel.RoomViewmodel

@Composable
fun CreateRoom(navHostController: NavHostController,viewmodel: RoomViewmodel) {

    val status by viewmodel.status.collectAsState()
    val roomData by viewmodel.roomData.collectAsState()
 //   val navigationToMain by viewmodel.navigateToMain.collectAsState()

    var username by remember { mutableStateOf("") }
    var roomname by remember { mutableStateOf("") }

    var showUsernameError by remember { mutableStateOf(false) }
    var showRoomnameError by remember { mutableStateOf(false) }

    val usernameValid = username.isNotBlank()
    val roomnameValid = roomname.isNotBlank()


    LaunchedEffect(roomData) {
        if (roomData != null) {
            Log.d("Navigation", "CreateRoom -> roomData received, navigating to MainScreen")
            navHostController.navigate(Route.MainScreen.route) {
                popUpTo(Route.HomeScreen.route) { inclusive = true }
            }
            viewmodel.clearRoomData()
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
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "Create Room",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    if (it.isNotBlank()) showUsernameError = false
                },
                placeholder = {
                    if (showUsernameError) {
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
                    focusedIndicatorColor = if (showUsernameError) Color.Red else Color(
                        0xFF00BFFF
                    ),
                    unfocusedLabelColor = if (showUsernameError) Color.Red else Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = roomname,
                onValueChange = {
                    roomname = it
                    if (roomname.isNotBlank()) showRoomnameError = false
                },
                placeholder = {
                    if (showRoomnameError) {
                        Text("Please choose a room name", color = Color.Red)
                    } else {
                        Text("Choose a room name", color = Color.Gray)
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
                    focusedIndicatorColor = if (showRoomnameError) Color.Red else Color(
                        0xFF00BFFF
                    ),
                    unfocusedLabelColor = if (showRoomnameError) Color.Red else Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    showUsernameError = !usernameValid
                    showRoomnameError = !roomnameValid

                    if (usernameValid && roomnameValid) {
                        viewmodel.createRoom(username, roomName = roomname)
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
                                Color(0xFF00C6FF),
                                Color(0xFF0072FF)
                            )
                        )
                    )
            ) {
                Text("Create Room", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}