package com.howtokaise.nexttune.domain.data

sealed class AppState {
    data object Loading : AppState()
    data class Error(val message: String) : AppState()
    data class RoomCreated(val roomCode: Int) : AppState()
    data class RoomJoined(val roomData: RoomData) : AppState()
    data class InRoom(val roomData: RoomData) : AppState()
}