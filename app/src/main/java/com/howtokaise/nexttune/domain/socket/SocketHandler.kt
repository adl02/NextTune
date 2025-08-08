package com.howtokaise.nexttune.domain.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object SocketHandler {
    private lateinit var socket: Socket

    fun initSocket(serverUrl : String){
        try {
           // val opts = IO.Options()
            socket = IO.socket(serverUrl)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun getSocket(): Socket = socket

    fun connect(){
        socket.connect()
    }

    fun disconnect(){
        socket.disconnect()
    }
}