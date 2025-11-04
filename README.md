# 🚀 NextTune

**NextTune** is a collaborative YouTube streaming app where friends can create or join rooms to watch videos together and chat in real-time — just like being in the same room, even when miles apart.

---

## 🎬 Overview

NextTune synchronizes YouTube playback across multiple users while enabling real-time chatting and playlist control.  
Perfect for music sessions, watch parties, or collaborative playlists!

This Android app is built to work with the **NextTune Web backend**, developed by [@prakash-ydv](https://github.com/prakash-ydv), which powers real-time room management and video synchronization using **Socket.IO** and **Node.js**.

---

## ✨ Features

- 🎥 **Synchronized YouTube Playback** — Everyone watches the same moment, together.
- 💬 **Real-Time Chat** — Chat with other members instantly.
- 🧩 **Create or Join Rooms** — Host private or public watch sessions.
- 🎶 **Shared Queue** — Add, remove, or play songs collaboratively.
- 🔒 **Secure Room Handling** — Unique room IDs and admin control.
- 📱 **Modern Jetpack Compose UI** — Clean, minimal, and responsive.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-------------|
| 🧩 UI | **Jetpack Compose (Material 3)** |
| 🎬 Video | **YouTube Player API** |
| 🔗 Networking | **Retrofit + YouTube Data API v3** |
| ⚡ Realtime | **Socket.IO (Node.js backend by [@prakash-ydv](https://github.com/prakash-ydv))** |
| 🧠 Architecture | **MVVM + Coroutines + Flow** |

---

## 🌐 Backend Information

The **backend & web version** of NextTune was developed by [@prakash-ydv](https://github.com/prakash-ydv)  
using **Node.js**, **Express.js**, and **Socket.IO**, handling:
- Room creation & synchronization
- Real-time chat and video events
- Integration with **YouTubePlayer API**

My **Android version** connects to the same backend for a seamless cross-platform experience.

---

## 📸 Screenshots

| Home | Search | Participants |
|------|---------|--------------|
| ![Home](./screenshots/home.jpg) | ![Search](./screenshots/search.jpg) | ![Participants](./screenshots/participants.jpg) |

| Main | Settings | Splash |
|------|-----------|--------|
| ![Main](./screenshots/main.jpg) | ![Settings](./screenshots/setting.jpg) | ![Splash](./screenshots/splash.jpg) |

---

## 👥 Credits

- **Android App:** [@Adil Jawed](https://github.com/adl02)
- **Backend & Web App:** [@prakash-ydv](https://github.com/prakash-ydv)

---

## ⭐ If you like this project

If you found **NextTune** helpful or inspiring, please ⭐ **star the repo** to support both developers!

---

