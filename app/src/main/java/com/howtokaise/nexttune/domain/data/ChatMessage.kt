package com.howtokaise.nexttune.domain.data

data class ChatMessage(
    val name: String,
    val message: String,
    val time: Long = System.currentTimeMillis(),
    val isAdmin: Boolean = false,
    val isMod: Boolean = false
)