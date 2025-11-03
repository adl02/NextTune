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
import org.json.JSONArray
import org.json.JSONObject

class RoomViewmodel : ViewModel() {

    private lateinit var socket: Socket

    private val _roomData = MutableStateFlow<JSONObject?>(null)
    val roomData = _roomData.asStateFlow()

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: SnapshotStateList<ChatMessage> = _messages

    private var listenersAttached = false

    private var _myName: String? = null
    val myName: String? get() = _myName

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
                updateChatFromJsonArray(data?.getJSONArray("chatInfo"))
            }
        }

        // user joined
        socket.on("user-joined") { args ->
            Log.d("SocketEvent", "user-joined -> $args")
            val data = args.getOrNull(0) as? JSONObject
            viewModelScope.launch {
                _roomData.value = data
                updateChatFromJsonArray(data?.getJSONArray("chatInfo"))
            }
        }

        socket.on("updated-chat") { args ->
            val chatArray = args.getOrNull(0) as? JSONArray ?: return@on
            viewModelScope.launch {
                updateChatFromJsonArray(chatArray)
            }
        }
    }

    private fun updateChatFromJsonArray(array: JSONArray?){
        if (array == null) return
        _messages.clear()
        for (i in 0 until array.length()){
            val obj = array.getJSONObject(i)
            val name = obj.optString("name", "Unknown")
            val message = obj.optString("message", "")
            val timeLong = try {
                obj.getLong("time")
            } catch (e: Exception) {
                // if backend already sent formatted string, try to parse it (fallback)
                System.currentTimeMillis()
            }
            _messages.add(
                ChatMessage(
                    name = name,
                    message = message,
                    time = timeLong,
                    isAdmin = obj.optBoolean("isAdmin", false),
                    isMod = obj.optBoolean("isMod", false)
                )
            )
        }
    }

    fun formatUnixTimeToHHmm(timestampMillis: Long): String {
        val date = java.util.Date(timestampMillis)
        val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        return sdf.format(date)
    }


    fun sendMessage(roomCode: String, name: String, message: String) {
        if (message.isBlank()) return
        viewModelScope.launch {
            try {
                if (!::socket.isInitialized || !socket.connected()) {
                    SocketHandler.connect()
                    delay(200)
                }

                val data = JSONObject().apply {
                    put("roomCode", roomCode)
                    put("name", name)
                    put("message", message)
                    put("isAdmin", false)
                    put("isMod", false)
                    put("time", System.currentTimeMillis())
                }

                socket.emit("send-message", data)
                Log.d("SocketEmit", "send-message -> $data")
            } catch (e: Exception) {
                Log.e("RoomViewmodel", "sendMessage error: ${e.message}")
            }
        }
    }

    fun clearRoomData() {
        viewModelScope.launch { _roomData.value = null }
    }

    fun createRoom(name: String, roomName: String) {
        _myName = name
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
