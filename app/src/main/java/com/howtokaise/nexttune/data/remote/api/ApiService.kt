package com.howtokaise.nexttune.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search{query}")
    suspend fun searchVideos(@Path("query") query: String): List<YouTubeSearchResponse>
}