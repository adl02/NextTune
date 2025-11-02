package com.howtokaise.nexttune.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howtokaise.nexttune.domain.data.ChatMessage
import com.howtokaise.nexttune.domain.socket.SocketHandler
import io.socket.client.Socket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class RoomViewmodel : ViewModel() {

    private lateinit var socket: Socket

    private val _roomData = MutableStateFlow<JSONObject?>(null)
    val roomData = _roomData.asStateFlow()

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: SnapshotStateList<ChatMessage> = _messages

    private var listenersAttached = false

    init {
        connectToServer()
    }

    fun connectToServer() {
        try {
            SocketHandler.initSocket("http://192.168.1.4:3000")
            socket = SocketHandler.getSocket()
        } catch (e: Exception) {
            Log.e("RoomViewmodel", "Socket init error: ${e.message}")
            return
        }

        if (!listenersAttached) {
            attachListeners()
            listenersAttached = true
        }

        SocketHandler.connect()
    }

    private fun attachListeners() {
        // room created
        socket.on("room-created") { args ->
            Log.d("SocketEvent", "room-created -> $args")
            val data = args.getOrNull(0) as? JSONObject
            viewModelScope.launch {
                _roomData.value = data
            }
        }

        // user joined
        socket.on("user-joined") { args ->
            Log.d("SocketEvent", "user-joined -> $args")
            val data = args.getOrNull(0) as? JSONObject
            viewModelScope.launch {
                _roomData.value = data
            }
        }
    }

    fun clearRoomData() {
        viewModelScope.launch { _roomData.value = null }
    }

    fun createRoom(name: String, roomName: String) {
        viewModelScope.launch {
            try {
                if (!::socket.isInitialized || !socket.connected()) {
                    Log.d("SocketEmit", "Socket not connected — connecting before emit")
                    SocketHandler.connect()
                    delay(250)
                }

                val data = JSONObject().apply {
                    put("name", name)
                    put("roomName", roomName)
                }
                Log.d("SocketEmit", "emit create-room -> $data")
                socket.emit("create-room", data)
            } catch (e: Exception) {
                Log.e("RoomViewmodel", "createRoom error: ${e.message}")
            }
        }
    }

    fun joinRoom(name: String, roomCode: String) {
        viewModelScope.launch {
            try {
                if (!::socket.isInitialized || !socket.connected()) {
                    Log.d("SocketEmit", "Socket not connected — connecting before emit")
                    SocketHandler.connect()
                    delay(250)
                }

                val data = JSONObject().apply {
                    put("joinRoomName", name)
                    put("joinRoomCode", roomCode)
                }
                Log.d("SocketEmit", "emit join-room -> $data")
                socket.emit("join-room", data)
            } catch (e: Exception) {
                Log.e("RoomViewmodel", "joinRoom error: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            SocketHandler.disconnect()
            Log.d("RoomViewmodel", "onCleared: socket disconnected")
        } catch (e: Exception) {
            Log.e("RoomViewmodel", "onCleared error: ${e.message}")
        }
    }
}
