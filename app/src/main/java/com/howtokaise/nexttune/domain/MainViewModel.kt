package com.howtokaise.nexttune.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject

// Your custom data classes (assuming they're in the same package)
import com.howtokaise.nexttune.domain.data.AppState
import com.howtokaise.nexttune.domain.data.ChatMessage
import com.howtokaise.nexttune.domain.data.RoomData
import com.howtokaise.nexttune.domain.data.UserInfo
import com.howtokaise.nexttune.domain.data.VideoInfo
import com.howtokaise.nexttune.domain.data.VideoItem

// MainViewModel.kt
class MainViewModel : ViewModel() {
    private lateinit var socket: Socket
    private val _appState = mutableStateOf<AppState>(AppState.Loading)
    val appState: State<AppState> = _appState

    private val _roomData = mutableStateOf<RoomData?>(null)
    val roomData: State<RoomData?> = _roomData

    private val _currentVideoId = mutableStateOf("")
    val currentVideoId: State<String> = _currentVideoId

    private val _chatMessages = mutableStateListOf<ChatMessage>()
    val chatMessages: SnapshotStateList<ChatMessage> = _chatMessages

    private val _videoQueue = mutableStateListOf<VideoItem>()
    val videoQueue: SnapshotStateList<VideoItem> = _videoQueue

    private val _users = mutableStateListOf<UserInfo>()
    val users: SnapshotStateList<UserInfo> = _users

    private val _isPlaying = mutableStateOf(false)
    val isPlaying: State<Boolean> = _isPlaying

    private var userName: String = ""
    private var roomCode: Int = 0

    init {
        initSocket()
    }

    private fun initSocket() {
        try {
            socket = IO.socket("https://nexttune.onrender.com")
            setupSocketListeners()
            socket.connect()
        } catch (e: Exception) {
            _appState.value = AppState.Error("Connection failed: ${e.message}")
        }
    }

    private fun setupSocketListeners() {
        socket.on(Socket.EVENT_CONNECT) {
            _appState.value = AppState.Loading
        }

        socket.on("room-created") { args ->
            val data = args[0] as JSONObject
            parseRoomData(data)
            _appState.value = AppState.RoomCreated(roomCode)
        }

        socket.on("user-joined") { args ->
            val data = args[0] as JSONObject
            parseRoomData(data)
            _appState.value = AppState.RoomJoined(_roomData.value!!)
        }

        socket.on("sync-users") { args ->
            val usersArray = args[0] as JSONArray
            parseUsers(usersArray)
        }

        socket.on("queue-updated") { args ->
            val queueArray = args[0] as JSONArray
            parseVideoQueue(queueArray)
        }

        socket.on("sync-play-from-queue") { args ->
            val data = args[0] as JSONObject
            _currentVideoId.value = data.getString("videoId")
            _isPlaying.value = true
        }

        socket.on("sync-play-video") { args ->
            val data = args[0] as JSONObject
            _currentVideoId.value = data.getString("videoId")
            _isPlaying.value = true
        }

        socket.on("sync-pause-video") {
            _isPlaying.value = false
        }

        socket.on("updated-chat") { args ->
            val chatArray = args[0] as JSONArray
            parseChatMessages(chatArray)
        }

        socket.on("room-not-found") {
            _appState.value = AppState.Error("Room not found")
        }
    }

    // Parsing functions
    private fun parseRoomData(data: JSONObject) {
        roomCode = data.getInt("roomCode")
        val videoInfoJson = data.getJSONObject("videoInfo")
        val chatInfoJson = data.getJSONArray("chatInfo")
        val usersJson = data.getJSONArray("users")

        val videoInfo = VideoInfo(
            isPlaying = videoInfoJson.getBoolean("isPlaying"),
            currentVideoId = videoInfoJson.getString("currentVideoId"),
            startedAt = videoInfoJson.getLong("startedAt"),
            pausedAt = videoInfoJson.optLong("pausedAt").takeIf { it > 0 },
            queue = parseVideoQueue(videoInfoJson.getJSONArray("queue"))
        )

        _roomData.value = RoomData(
            roomCode = roomCode,
            roomName = data.getString("roomName"),
            adminId = data.getString("adminId"),
            users = parseUsers(usersJson),
            videoInfo = videoInfo,
            chatInfo = parseChatMessages(chatInfoJson)
        )
    }

    private fun parseUsers(usersArray: JSONArray): List<UserInfo> {
        return List(usersArray.length()) { i ->
            val user = usersArray.getJSONObject(i)
            UserInfo(
                name = user.getString("name"),
                userId = user.getString("userId"),
                isHost = user.getBoolean("isHost"),
                isMod = user.getBoolean("isMod"),
                status = user.getString("status"),
                isLeft = user.getBoolean("isLeft")
            )
        }.also {
            _users.clear()
            _users.addAll(it)
        }
    }

    private fun parseVideoQueue(queueArray: JSONArray): List<VideoItem> {
        return List(queueArray.length()) { i ->
            val video = queueArray.getJSONObject(i)
            VideoItem(
                videoId = video.getString("videoId"),
                title = video.getString("title"),
                thumbnailUrl = video.getString("thumbnailUrl")
            )
        }.also {
            _videoQueue.clear()
            _videoQueue.addAll(it)
        }
    }

    private fun parseChatMessages(chatArray: JSONArray): List<ChatMessage> {
        return List(chatArray.length()) { i ->
            val msg = chatArray.getJSONObject(i)
            ChatMessage(
                name = msg.getString("name"),
                message = msg.getString("message"),
                time = msg.getLong("time"),
                isAdmin = msg.getBoolean("isAdmin"),
                isMod = msg.getBoolean("isMod")
            )
        }.also {
            _chatMessages.clear()
            _chatMessages.addAll(it)
        }
    }

    // Room Actions
    fun createRoom(name: String, roomName: String) {
        userName = name
        socket.emit("create-room", JSONObject().apply {
            put("name", name)
            put("roomName", roomName)
        })
    }

    fun joinRoom(name: String, joinRoomCode: Int) {
        userName = name
        roomCode = joinRoomCode
        socket.emit("join-room", JSONObject().apply {
            put("joinRoomName", name)
            put("joinRoomCode", joinRoomCode)
        })
    }

    fun addVideoToQueue(video: VideoItem) {
        socket.emit("add-video-id-to-queue", JSONObject().apply {
            put("vdo", JSONObject().apply {
                put("id", JSONObject().apply {
                    put("videoId", video.videoId)
                })
                put("title", video.title)
                put("thumbnailUrl", video.thumbnailUrl)
            })
            put("roomCode", roomCode)
        })
    }

    fun removeVideoFromQueue(videoId: String) {
        socket.emit("remove-video-from-queue", JSONObject().apply {
            put("videoId", videoId)
            put("roomCode", roomCode)
        })
    }

    fun playVideoFromQueue(videoId: String) {
        socket.emit("play-video-from-queue", JSONObject().apply {
            put("videoId", videoId)
            put("roomCode", roomCode)
        })
    }

    fun sendMessage(message: String) {
        socket.emit("send-message", JSONObject().apply {
            put("roomCode", roomCode)
            put("name", userName)
            put("message", message)
            put("isAdmin", _users.firstOrNull { it.userId == socket.id() }?.isHost ?: false)
            put("isMod", _users.firstOrNull { it.userId == socket.id() }?.isMod ?: false)
            put("time", System.currentTimeMillis())
        })
    }

    fun syncPlayVideo(videoId: String) {
        socket.emit("sync-play-video", JSONObject().apply {
            put("roomCode", roomCode)
            put("currentVideoId", videoId)
        })
    }

    fun syncPauseVideo() {
        socket.emit("sync-pause-video", JSONObject().apply {
            put("roomCode", roomCode)
        })
    }

    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
    }
}