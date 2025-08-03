package com.howtokaise.nexttune.domain.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.howtokaise.nexttune.presentation.YouTubeViewModel
import com.howtokaise.nexttune.presentation.screens.HomeScreen
import com.howtokaise.nexttune.presentation.screens.LiveChat
import com.howtokaise.nexttune.presentation.screens.MainScreen
import com.howtokaise.nexttune.presentation.screens.MusicList
import com.howtokaise.nexttune.presentation.screens.Participants
import com.howtokaise.nexttune.presentation.screens.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route
    ) {
        composable(Route.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Route.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Route.MainScreen.route){
            MainScreen(navController)
        }
        composable(Route.MusicList.route){
            val youTubeVm: YouTubeViewModel = viewModel()
            MusicList(youTubeVm)
        }
        composable(Route.Participants.route){
           Participants(navController)
        }
    }
}