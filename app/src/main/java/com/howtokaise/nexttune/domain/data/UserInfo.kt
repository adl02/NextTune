package com.howtokaise.nexttune.domain.data

data class UserInfo(
    val name: String,
    val userId: String,
    val isHost: Boolean,
    val isMod: Boolean,
    val status: String = "watching",
    val isLeft: Boolean = false
)