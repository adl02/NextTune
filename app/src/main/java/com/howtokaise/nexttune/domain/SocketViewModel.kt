package com.howtokaise.nexttune.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howtokaise.nexttune.data.remote.api.SocketManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SocketViewModel : ViewModel() {

    private val _roomCode = MutableStateFlow<String?>(null)
    val roomCode: StateFlow<String?> = _roomCode

    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        SocketManager.initSocket()
        SocketManager.connect()
        setupListeners()
    }

    private fun setupListeners() {
        SocketManager.on("connect") {
            viewModelScope.launch {
                _connectionState.value = true
            }
        }

        SocketManager.on("roomCreated") { data ->
            viewModelScope.launch {
                val room = data.getString("roomCode")
                _roomCode.value = room
            }
        }

        SocketManager.on("joinedRoom") { data ->
            viewModelScope.launch {
                val success = data.optBoolean("success", false)
                if (success) {
                    val room = data.getString("roomCode")
                    _roomCode.value = room
                } else {
                    _errorMessage.value = data.optString("message", "Failed to join room")
                }
            }
        }

        SocketManager.on("error") { data ->
            viewModelScope.launch {
                _errorMessage.value = data.optString("message", "An error occurred")
            }
        }
    }

    fun createRoom(userName: String, roomName: String) {
        val json = JSONObject().apply {
            put("userName", userName)
            put("roomName", roomName)
        }
        SocketManager.emit("createRoom", json)
    }

    fun joinRoom(userName: String, roomCode: String) {
        val json = JSONObject().apply {
            put("userName", userName)
            put("roomCode", roomCode)
        }
        SocketManager.emit("joinRoom", json)
    }

    override fun onCleared() {
        super.onCleared()
        SocketManager.disconnect()
    }
}
