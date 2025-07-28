package com.howtokaise.nexttune.data.remote.api

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket

// SocketManager.kt
object SocketManager {
    private lateinit var socket: Socket

    fun initSocket() {
        try {
            socket = IO.socket("https://nexttune.onrender.com")
        } catch (e: Exception) {
            Log.e("SocketManager", "Socket initialization failed", e)
        }
    }

    fun connect() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    fun getSocket(): Socket = socket
}