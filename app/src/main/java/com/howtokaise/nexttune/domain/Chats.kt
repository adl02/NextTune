package com.howtokaise.nexttune.domain

import android.os.Message
import com.howtokaise.nexttune.R

data class Chats(
    val profile:Int,
    val name : String,
    val time : String,
    val message: String
)
val chat = listOf(
    Chats(
        profile = R.drawable.appicon,
        name = "NextTune",
        time = "16:00",
        message = "Welcome to the room, Invite and enjoy with your loved ones!"
    ),
    Chats(
        profile = R.drawable.appicon,
        name = "NextTune",
        time = "1:00",
        message = "he room, Invite and enjoy with your loved ones!"
    ),
    Chats(
        profile = R.drawable.appicon,
        name = "NextTune",
        time = "16:00",
        message = ", Invite and enjoy with your loved ones!"
    )
)