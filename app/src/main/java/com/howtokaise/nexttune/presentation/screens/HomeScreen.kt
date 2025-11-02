package com.howtokaise.nexttune.presentation.screens

import AnimatedGlowingBackground
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.presentation.viewmodel.RoomViewmodel


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun HomeScreen(navHostController: NavHostController) {

    AnimatedGlowingBackground { }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        CreateRoom(navHostController, viewmodel = RoomViewmodel())

        Text(
            text = "OR",
            color = Color.Gray,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        JoinRoom(navHostController, viewmodel =  RoomViewmodel())
    }
}