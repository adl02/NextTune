package com.howtokaise.nexttune.domain.data

data class UserInfo(
    val name: String,
    val userId: String,
    val isHost: Boolean,
    val isMod: Boolean,
    val status: String = "watching",
    val isLeft: Boolean = false
)

data class ChatMessage(
    val name: String,
    val message: String,
    val time : Long,
    val isAdmin: Boolean = false,
    val isMod: Boolean = false
)

data class VideoInfo(
    val isPlaying: Boolean = false,
    val currentVideoId: String = "",
    val startedAt: Long = 0,
    val pausedAt: Long? = null,
    val queue: List<VideoItem> = emptyList()
)

data class VideoItem(
    val videoId: String,
    val title: String,
    val thumbnailUrl: String
)