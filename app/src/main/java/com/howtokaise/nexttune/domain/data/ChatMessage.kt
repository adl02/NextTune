package com.howtokaise.nexttune.domain.data

import androidx.compose.material3.Text

data class ChatMessage(
    val name: String,
    val message: String,
   // val time: Long = System.currentTimeMillis(),
    val time : String,
    val isAdmin: Boolean = false,
    val isMod: Boolean = false
)

val chat = listOf(
    ChatMessage(
        name = "Adil",
        message = "hello",
        time = "00:00",
        isAdmin = true,
        isMod = false
    )
)