package com.howtokaise.nexttune.presentation

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
import com.howtokaise.nexttune.data.remote.api.SocketManager
import com.howtokaise.nexttune.domain.MainViewModel
import com.howtokaise.nexttune.domain.data.AppState
import com.howtokaise.nexttune.domain.navigation.NavGraph
import com.howtokaise.nexttune.presentation.ui.theme.NextTuneTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SocketManager.initSocket()
        SocketManager.connect()
        enableEdgeToEdge()

        setContent {
            NextTuneTheme {
                val navController = rememberNavController()
                val appState by viewModel.appState
                var showError by remember { mutableStateOf(false) }
                var errorMessage by remember { mutableStateOf("") }

                // Handle state changes and navigation
                LaunchedEffect(appState) {
                    when (appState) {
                        is AppState.RoomCreated -> {
                            val roomCode = (appState as AppState.RoomCreated).roomCode
                            navController.navigate("main/$roomCode")
                        }
                        is AppState.RoomJoined -> {
                            val roomCode = (appState as AppState.RoomJoined).roomData.roomCode
                            navController.navigate("main/$roomCode")
                        }
                        is AppState.Error -> {
                            errorMessage = (appState as AppState.Error).message
                            showError = true
                        }
                        is AppState.InRoom -> {
                            // Already in room, no action needed
                        }
                        AppState.Loading -> {
                            // Show loading indicator, no navigation needed
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        showError = showError,
                        errorMessage = errorMessage,
                        onDismissError = { showError = false }
                    )

                    // Add a loading indicator for AppState.Loading
                    if (appState == AppState.Loading) {
                        // Show your loading UI here
                    }
                }
            }
        }
    }
}