package com.howtokaise.nexttune.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howtokaise.nexttune.data.remote.api.RetrofitInstance
import com.howtokaise.nexttune.domain.data.YouTubeSearchResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class YouTubeViewModel : ViewModel() {
    private val _results = MutableStateFlow<List<YouTubeSearchResponse>>(emptyList())
    val results: StateFlow<List<YouTubeSearchResponse>> = _results.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _results.value = RetrofitInstance.api.searchVideos(query)
            } catch (e: Exception) {
                _errorMessage.value = "Search failed: ${e.message}"
                Log.e("YouTubeSearch", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearResults() {
        _results.value = emptyList()
        _errorMessage.value = null
    }
}