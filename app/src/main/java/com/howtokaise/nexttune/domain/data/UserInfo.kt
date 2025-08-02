package com.howtokaise.nexttune.domain.data

data class UserInfo(
    val name: String,
    val userId: String,
    val isHost: Boolean,
    val isMod: Boolean,
    val status: String = "watching",
    val isLeft: Boolean = false
)

val people = listOf(
    UserInfo(
        name = "adil",
        userId = "12345",
        isHost = true,
        isMod = false,
        status = "watching",
        isLeft = false
    )
)