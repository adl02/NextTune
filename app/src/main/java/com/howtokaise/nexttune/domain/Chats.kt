package com.howtokaise.nexttune.domain

data class ChatMessage(
    val name : String,
    val time : String,
    val message: String,
    val isAdmin: Boolean = false,
    val isMod : Boolean = false
)