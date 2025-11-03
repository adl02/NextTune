package com.howtokaise.nexttune.domain.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.howtokaise.nexttune.presentation.viewmodel.RoomViewmodel
import com.howtokaise.nexttune.presentation.viewmodel.YouTubeViewModel
import com.howtokaise.nexttune.presentation.screens.HomeScreen
import com.howtokaise.nexttune.presentation.screens.MainScreen
import com.howtokaise.nexttune.presentation.screens.MusicList
import com.howtokaise.nexttune.presentation.screens.Participants
import com.howtokaise.nexttune.presentation.screens.SettingScreen
import com.howtokaise.nexttune.presentation.screens.SplashScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph(
    navController: NavHostController, roomViewmodel: RoomViewmodel
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
            MainScreen(navController, viewmodel = roomViewmodel)
        }
        composable(Route.MusicList.route){
            val youTubeVm: YouTubeViewModel = viewModel()
            MusicList(youTubeVm)
        }
        composable(Route.Participants.route){
           Participants(navController, viewmodel = roomViewmodel)
        }
    }
}