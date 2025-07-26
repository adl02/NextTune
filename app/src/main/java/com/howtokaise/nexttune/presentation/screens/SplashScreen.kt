package com.howtokaise.nexttune.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.R
import com.howtokaise.nexttune.domain.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF1E3A8A),
                        Color(0xFF0F172A)
                    )
                )
            )
    ){
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painterResource(id = R.drawable.appicon),
                contentDescription = "slap",
                modifier = Modifier
                    .size(350.dp),
            )

            Text(
                text = "Let's have some fun !",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        Text(
            text = "    DESIGN BY \n How To Kaise",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
        )

    }
    LaunchedEffect(Unit) {
        delay(1500)
        navHostController.navigate(Route.HomeScreen.route){
            popUpTo(Route.SplashScreen.route){
                inclusive = true
            }
        }
    }
}