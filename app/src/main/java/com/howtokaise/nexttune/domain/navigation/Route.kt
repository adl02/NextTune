package com.howtokaise.nexttune.domain.navigation

sealed class Route(
    val route: String
) {
    object SplashScreen : Route("SplashScreen")
    object HomeScreen : Route("HomeScreen")
    object MainScreen : Route("MainScreen")
    object MusicList : Route("MusicList")
    object Participants : Route("Participants")
}