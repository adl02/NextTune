package com.howtokaise.nexttune.domain.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object SocketHandler {
    lateinit var socket: Socket

    fun initSocket(){
        try {
            val opts = IO.Options()
            socket = IO.socket("http://192.168.1.4:3000",opts)

            // Listen for connection success
            socket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "✅ Connected to server")
            }

            // Listen for disconnection
            socket.on(Socket.EVENT_DISCONNECT) {
                Log.d("SocketIO", "❌ Disconnected from server")
            }

            // Optional: listen for connection errors
            socket.on(Socket.EVENT_CONNECT_ERROR) {
                Log.e("SocketIO", "❌ Connection Error: ${it[0]}")
            }

        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun connect(){
        socket.connect()
    }

    fun disconnect(){
        socket.disconnect()
    }

    fun emit(event: String, data: JSONObject) {
        socket.emit(event, data)
    }

    fun on(event: String, callback: (JSONObject) -> Unit) {
        socket.on(event) {
            if (it.isNotEmpty()) {
                callback(it[0] as JSONObject)
            }
        }
    }
}