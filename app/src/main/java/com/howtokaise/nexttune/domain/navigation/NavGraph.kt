package com.howtokaise.nexttune.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            MusicList()
        }
        composable(Route.Participants.route){
           Participants(navController)
        }
    }
}