package com.howtokaise.nexttune.domain.data

data class RoomData(
    val roomCode: Int,
    val roomName: String,
    val adminId: String,
    val users: List<UserInfo>,
    val videoInfo: VideoInfo,
    val chatInfo: List<ChatMessage>
)
