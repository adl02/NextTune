package com.howtokaise.nexttune.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.howtokaise.nexttune.domain.data.AppState
import com.howtokaise.nexttune.domain.navigation.NavGraph
import com.howtokaise.nexttune.domain.socket.SocketHandler
import com.howtokaise.nexttune.presentation.screens.MusicList
import com.howtokaise.nexttune.presentation.ui.theme.NextTuneTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SocketHandler.initSocket()
        SocketHandler.connect()
        enableEdgeToEdge()
        setContent {
            val NavHostController = rememberNavController()
            NextTuneTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) { innerPadding ->
                   // NavGraph(NavHostController)
                    MusicList(YouTubeViewModel())
                }
            }
        }
    }
}