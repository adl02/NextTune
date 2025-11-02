package com.howtokaise.nexttune.domain.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    private var socket: Socket? = null
    private var serverUrl: String = ""

    fun initSocket(url: String) {
        if (socket != null && serverUrl == url) {
            Log.d("SocketHandler", "Socket already initialized for $url")
            return
        }

        try {
            serverUrl = url
            socket = IO.socket(url)
            Log.d("SocketHandler", "Socket initialized: $url")
        } catch (e: URISyntaxException) {
            Log.e("SocketHandler", "initSocket error: ${e.message}")
        } catch (e: Exception) {
            Log.e("SocketHandler", "initSocket unexpected: ${e.message}")
        }
    }

    fun getSocket(): Socket {
        return socket ?: throw IllegalStateException("Socket not initialized. Call initSocket() first.")
    }

    fun connect() {
        socket?.let {
            if (!it.connected()) {
                it.connect()
                Log.d("SocketHandler", "Socket connecting...")
            } else {
                Log.d("SocketHandler", "Socket already connected")
            }
        } ?: run {
            Log.e("SocketHandler", "connect() called but socket is null")
        }
    }

    fun disconnect() {
        socket?.let {
            if (it.connected()) {
                it.disconnect()
                Log.d("SocketHandler", "Socket disconnected")
            }
        }
    }
}
