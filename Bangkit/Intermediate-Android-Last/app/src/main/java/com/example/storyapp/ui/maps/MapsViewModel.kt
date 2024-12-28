package com.example.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.data.stories.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val repository : StoryRepository) : ViewModel() {
    private val _mapsStatus = MutableLiveData<ApiResponse<StoryResponse>>()
    val mapsStatus : LiveData<ApiResponse<StoryResponse>> get() = _mapsStatus

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            repository.getStoriesWithMap().collect { response ->
                _mapsStatus.value = response
            }
        }
    }

}