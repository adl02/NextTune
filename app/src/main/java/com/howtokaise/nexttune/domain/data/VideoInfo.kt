package com.howtokaise.nexttune.domain.data

data class VideoInfo(
    val isPlaying: Boolean = false,
    val currentVideoId: String = "",
    val startedAt: Long = 0,
    val pausedAt: Long? = null,
    val queue: List<VideoItem> = emptyList()
)