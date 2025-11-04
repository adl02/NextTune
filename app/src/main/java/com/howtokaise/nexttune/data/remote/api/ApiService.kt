package com.howtokaise.nexttune.data.remote.api

import com.howtokaise.nexttune.domain.data.YouTubeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("search/{query}")
    suspend fun searchVideos(
        @Path("query") query: String
    ): List<YouTubeSearchResponse>
}