package com.howtokaise.nexttune.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.howtokaise.nexttune.domain.navigation.NavGraph
import com.howtokaise.nexttune.presentation.screens.HomeScreen
import com.howtokaise.nexttune.presentation.screens.MainScreen
import com.howtokaise.nexttune.presentation.screens.Participants
import com.howtokaise.nexttune.presentation.ui.theme.NextTuneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val NavHostController = rememberNavController()
            NextTuneTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) { innerPadding ->
                   NavGraph(NavHostController)
                  //  Participants(NavHostController)
                  //  HomeScreen(NavHostController)
                }
            }
        }
    }
}
