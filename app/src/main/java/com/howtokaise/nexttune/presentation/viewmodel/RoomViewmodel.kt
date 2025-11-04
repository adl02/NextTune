package com.howtokaise.nexttune.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.domain.data.ChatMessage
import com.howtokaise.nexttune.domain.data.UserInfo
import com.howtokaise.nexttune.domain.data.VideoItem
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

    // Setting states
    private val _isSettingsOpen = MutableStateFlow(false)
    val isSettingsOpen = _isSettingsOpen.asStateFlow()

    private val _showLeaveDialog = MutableStateFlow(false)
    val showLeaveDialog = _showLeaveDialog.asStateFlow()

    // Room States
    private val _participants = MutableStateFlow<List<UserInfo>>(emptyList())
    val participants = _participants.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin = _isAdmin.asStateFlow()

    private val _isMod = MutableStateFlow(false)
    val isMod = _isMod.asStateFlow()

    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _roomData = MutableStateFlow<JSONObject?>(null)
    val roomData = _roomData.asStateFlow()

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: SnapshotStateList<ChatMessage> = _messages

    // Video states
    private val _currentVideoId = MutableStateFlow("")
    val currentVideoId = _currentVideoId.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _currentTime = MutableStateFlow(0f)
    val currentTime = _currentTime.asStateFlow()

    private val _videoQueue = MutableStateFlow<List<VideoItem>>(emptyList())
    val videoQueue = _videoQueue.asStateFlow()

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
                _participants.value = parseUsersArray(data?.optJSONArray("users"))
                _roomData.value = data
                updateChatFromJsonArray(data?.getJSONArray("chatInfo"))
                _isAdmin.value = true
                _isMod.value = true

                // Video info handle karein
                val videoInfo = data?.optJSONObject("videoInfo")
                videoInfo?.let {
                    _currentVideoId.value = it.optString("currentVideoId", "")
                    _isPlaying.value = it.optBoolean("isPlaying", false)
                    _videoQueue.value = parseVideoQueue(it.optJSONArray("queue"))
                }

                // Set current user info for admin
                val users = parseUsersArray(data?.optJSONArray("users"))
                val adminUser = users.find { it.isHost }
                adminUser?.let {
                    _currentUser.value = it
                    _myName = it.name
                }
            }
        }

        // user joined
        socket.on("user-joined") { args ->
            Log.d("SocketEvent", "user-joined -> $args")
            val data = args.getOrNull(0) as? JSONObject
            viewModelScope.launch {
                val serverUsers = parseUsersArray(data?.optJSONArray("users"))
                _participants.value = serverUsers
                _roomData.value = data
                updateChatFromJsonArray(data?.getJSONArray("chatInfo"))
                _isAdmin.value = data?.optBoolean("isAdmin") ?: false
                _isMod.value = data?.optBoolean("isMod") ?: false

                // Video info handle karein
                val videoInfo = data?.optJSONObject("videoInfo")
                videoInfo?.let {
                    _currentVideoId.value = it.optString("currentVideoId", "")
                    _isPlaying.value = it.optBoolean("isPlaying", false)
                    _videoQueue.value = parseVideoQueue(it.optJSONArray("queue"))
                }

                // Set current user info for participant
                val myNameFromData = data?.optString("myName")
                myNameFromData?.let { name ->
                    _myName = name
                    val currentUserInfo = serverUsers.find { it.name == name }
                    currentUserInfo?.let {
                        _currentUser.value = it
                    }
                }
            }
        }

        socket.on("updated-chat") { args ->
            val chatArray = args.getOrNull(0) as? JSONArray ?: return@on
            viewModelScope.launch {
                updateChatFromJsonArray(chatArray)
            }
        }

        // Video sync listeners
        socket.on("sync-play-video") { args ->
            val data = args.getOrNull(0) as? JSONObject
            viewModelScope.launch {
                val videoId = data?.optString("videoId")
                val currentTime = data?.optDouble("currentTime")?.toFloat() ?: 0f

                if (!videoId.isNullOrEmpty()) {
                    _currentVideoId.value = videoId
                }
                _isPlaying.value = true
                _currentTime.value = currentTime

                Log.d("VideoSync", "Play video: $videoId, time: $currentTime")
            }
        }

        socket.on("sync-pause-video") { args ->
            viewModelScope.launch {
                _isPlaying.value = false
                Log.d("VideoSync", "Video paused")
            }
        }

        socket.on("sync-play-from-queue") { args ->
            val data = args.getOrNull(0) as? JSONObject
            viewModelScope.launch {
                val videoId = data?.optString("videoId")
                val currentTime = data?.optDouble("currentTime")?.toFloat() ?: 0f

                if (!videoId.isNullOrEmpty()) {
                    _currentVideoId.value = videoId
                }
                _isPlaying.value = true
                _currentTime.value = currentTime

                Log.d("VideoSync", "Play from queue: $videoId, time: $currentTime")
            }
        }

        socket.on("queue-updated") { args ->
            val queueArray = args.getOrNull(0) as? JSONArray
            viewModelScope.launch {
                val updatedQueue = parseVideoQueue(queueArray)
                _videoQueue.value = updatedQueue
                Log.d("VideoSync", "Queue updated: ${updatedQueue.size} videos")
            }
        }

        socket.on("sync-users") { args ->
            val array = args.getOrNull(0) as? JSONArray
            viewModelScope.launch {
                _participants.value = parseUsersArray(array)
            }
        }

        socket.on("user-left") { args ->
            val userId = args.getOrNull(0) as? String
            viewModelScope.launch {
                _participants.value = _participants.value.map { user ->
                    if (user.userId == userId) {
                        user.copy(status = "Left", isLeft = true)
                    } else {
                        user
                    }
                }
            }
        }

        socket.on("participants-updated") { args ->
            val array = args.getOrNull(0) as? JSONArray
            viewModelScope.launch {
                _participants.value = parseUsersArray(array)
            }
        }
    }

    // Video control functions

    fun searchAndAddVideo(query: String, youtubeViewModel: YouTubeViewModel) {
        viewModelScope.launch {
            youtubeViewModel.search(query)
        }
    }

    fun playNextVideoInQueue() {
        val queue = _videoQueue.value
        if (queue.isNotEmpty()) {
            val nextVideo = queue.first()
            playVideoFromQueue(nextVideo.videoId)
        }
    }

    fun playVideo(videoId: String? = null) {
        if (videoId != null) {
            _currentVideoId.value = videoId
        }

        val roomCode = _roomData.value?.optInt("roomCode")?.toString() ?: return
        if (_isAdmin.value || _isMod.value) {
            socket.emit("sync-play-video", JSONObject().apply {
                put("roomCode", roomCode)
                put("currentVideoId", videoId ?: _currentVideoId.value)
            })
        }
        _isPlaying.value = true
    }

    fun pauseVideo() {
        val roomCode = _roomData.value?.optInt("roomCode")?.toString() ?: return
        if (_isAdmin.value || _isMod.value) {
            socket.emit("sync-pause-video", JSONObject().apply {
                put("roomCode", roomCode)
            })
        }
        _isPlaying.value = false
    }

    fun seekTo(time: Float) {
        _currentTime.value = time
    }

    fun addVideoToQueue(videoItem: VideoItem) {
        val roomCode = _roomData.value?.optInt("roomCode")?.toString() ?: return
        val vdoJson = JSONObject().apply {
            put("id", JSONObject().apply {
                put("videoId", videoItem.videoId)
            })
            put("snippet", JSONObject().apply {
                put("title", videoItem.title)
                put("thumbnails", JSONObject().apply {
                    put("default", JSONObject().apply {
                        put("url", videoItem.thumbnailUrl)
                    })
                })
            })
        }

        socket.emit("add-video-id-to-queue", JSONObject().apply {
            put("vdo", vdoJson)
            put("roomCode", roomCode)
        })
    }

    fun removeVideoFromQueue(videoId: String) {
        val roomCode = _roomData.value?.optInt("roomCode")?.toString() ?: return
        socket.emit("remove-video-from-queue", JSONObject().apply {
            put("videoId", videoId)
            put("roomCode", roomCode)
        })
    }

    fun playVideoFromQueue(videoId: String) {
        val roomCode = _roomData.value?.optInt("roomCode")?.toString() ?: return
        if (_isAdmin.value || _isMod.value) {
            socket.emit("play-video-from-queue", JSONObject().apply {
                put("videoId", videoId)
                put("roomCode", roomCode)
            })
        }
    }

    // Helper functions
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

    private fun parseVideoQueue(array: JSONArray?): List<VideoItem> {
        if (array == null) return emptyList()
        val queue = mutableListOf<VideoItem>()
        for (i in 0 until array.length()) {
            val obj = array.optJSONObject(i) ?: continue
            val idObj = obj.optJSONObject("id")
            val snippetObj = obj.optJSONObject("snippet")

            if (idObj != null && snippetObj != null) {
                queue.add(
                    VideoItem(
                        videoId = idObj.optString("videoId", ""),
                        title = snippetObj.optString("title", "Unknown Title"),
                        thumbnailUrl = snippetObj.optJSONObject("thumbnails")
                            ?.optJSONObject("default")
                            ?.optString("url", "") ?: ""
                    )
                )
            }
        }
        return queue
    }

    private fun parseUsersArray(array: JSONArray?): List<UserInfo> {
        if (array == null) return emptyList()
        val list = mutableListOf<UserInfo>()
        for (i in 0 until array.length()) {
            val obj = array.optJSONObject(i) ?: continue
            list.add(
                UserInfo(
                    name = obj.optString("name", "Unknown"),
                    userId = obj.optString("userId", obj.optString("id", "user-${System.currentTimeMillis()}")),
                    isHost = obj.optBoolean("isHost", false),
                    isMod = obj.optBoolean("isMod", false),
                    status = obj.optString("status", "watching"),
                    isLeft = obj.optBoolean("isLeft", false)
                )
            )
        }
        return list
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
                    put("isAdmin", _isAdmin.value) // Use the actual admin status
                    put("isMod", _isMod.value)    // Use the actual mod status
                    put("time", System.currentTimeMillis())
                }

                socket.emit("send-message", data)
                Log.d("SocketEmit", "send-message -> $data")
            } catch (e: Exception) {
                Log.e("RoomViewmodel", "sendMessage error: ${e.message}")
            }
        }
    }

    fun clearRoomData() { viewModelScope.launch { _roomData.value = null } }

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
                socket.emit("create-room", data)

                // Create temporary admin user with proper host flag
                val adminUser = UserInfo(
                    name = name,
                    userId = "admin-temp", // This will be updated when room-created is received
                    isHost = true,
                    isMod = true,
                    status = "watching"
                )
                _currentUser.value = adminUser
                _participants.value = listOf(adminUser)
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

    // Setting functions
    fun openSettings() { _isSettingsOpen.value = true }

    fun closeSettings() { _isSettingsOpen.value = false }

    fun showLeaveConfirmation() { _showLeaveDialog.value = true }

    fun hideLeaveConfirmation() { _showLeaveDialog.value = false }

    fun leaveRoom(navController: NavHostController? = null) {
        viewModelScope.launch {
            try {
                SocketHandler.disconnect()

                // Clear all room data
                _roomData.value = null
                _participants.value = emptyList()
                _messages.clear()
                _isAdmin.value = false
                _isMod.value = false
                _currentUser.value = null
                _myName = null
                _isSettingsOpen.value = false
                _showLeaveDialog.value = false

                // Navigate to home screen
                navController?.navigate(com.howtokaise.nexttune.domain.navigation.Route.HomeScreen.route) {
                    popUpTo(com.howtokaise.nexttune.domain.navigation.Route.MainScreen.route) { inclusive = true }
                }
            } catch (e: Exception) {
                Log.e("RoomViewmodel", "leaveRoom error: ${e.message}")
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
