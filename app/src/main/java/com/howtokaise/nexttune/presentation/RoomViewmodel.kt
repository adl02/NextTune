package com.howtokaise.nexttune.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howtokaise.nexttune.domain.socket.SocketHandler
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class RoomViewmodel : ViewModel() {
    private lateinit var socket: Socket
    private val _status = MutableStateFlow("Disconnected")
    val status = _status.asStateFlow()

    private val _roomData = MutableStateFlow<JSONObject?>(null)
    val roomData = _roomData.asStateFlow()

    fun connectToServer(){
        SocketHandler.initSocket("http://192.168.1.12:3000")
        socket = SocketHandler.getSocket()

        socket.on(Socket.EVENT_CONNECT){
            viewModelScope.launch { _status.value = "Connected" }
        }

        socket.on("room-created"){ args ->
            val data = args[0] as JSONObject
            viewModelScope.launch { _roomData.value = data }
        }

        socket.on("room-not-found"){
            println("Room not found!")
        }

        SocketHandler.connect()
    }

    fun createRoom(name : String, roomName : String){
        val data = JSONObject()
        data.put("name",name)
        data.put("roomName", roomName)
        socket.emit("create-room",data)
    }

    fun joinRoom(name: String, roomCode : Int){
        val data = JSONObject()
        data.put("joinRoom",name)
        data.put("joinRoomCode", roomCode)
        socket.emit("join-room", data)
    }
}