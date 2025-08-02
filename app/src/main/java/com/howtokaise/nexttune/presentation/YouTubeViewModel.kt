package com.howtokaise.nexttune.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howtokaise.nexttune.data.remote.api.RetrofitInstance
import com.howtokaise.nexttune.domain.data.YouTubeSearchResponse
import kotlinx.coroutines.launch

class YouTubeViewModel : ViewModel() {
    private var results by mutableStateOf<List<YouTubeSearchResponse>>(emptyList())
        private set

    var  isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun search(query: String){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                results = RetrofitInstance.api.searchVideos(query)
            } catch (e : Exception){
                Log.e("Search","Error :${e.message}")
            }
        }
    }
}