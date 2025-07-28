package com.howtokaise.nexttune.data.remote.api

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object SocketManager {
    private lateinit var socket: Socket

    fun initSocket() {
        try {
            socket = IO.socket("https://nexttune.onrender.com")

            socket.on(Socket.EVENT_CONNECT) {
                println("✅ Socket connected!")
            }
            socket.on(Socket.EVENT_DISCONNECT) {
                println("❌ Socket disconnected!")
            }
            socket.on(Socket.EVENT_CONNECT_ERROR) {
                println("🚨 Connection error: ${it[0]}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun connect(){
        socket.connect()
    }

    fun disconnect(){
        socket.disconnect()
    }

    fun emit(event: String, data : JSONObject){
        socket.emit(event, data)
    }

    fun on(event: String, callback: (JSONObject) -> Unit) {
        socket.on(event) { args ->
            if (args.isNotEmpty()) {
                val rawData = args[0]
                val data = when (rawData) {
                    is JSONObject -> rawData
                    is String -> JSONObject(rawData)
                    else -> JSONObject(rawData.toString())
                }
                callback(data)
            }
        }
    }


    fun getSocket(): Socket = socket
}