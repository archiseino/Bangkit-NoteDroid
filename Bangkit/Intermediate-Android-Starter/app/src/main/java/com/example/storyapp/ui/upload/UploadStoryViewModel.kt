package com.example.storyapp.ui.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.data.stories.UploadResponse
import com.example.storyapp.data.stories.UploadStory
import com.example.storyapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadStoryViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {
    private val _uploadStatus = MutableLiveData<Event<ApiResponse<UploadResponse>>>()
    val uploadStatus : LiveData<Event<ApiResponse<UploadResponse>>> get() = _uploadStatus

    fun uploadStory(uploadStory: UploadStory) {
        viewModelScope.launch {
            storyRepository.uploadStory(uploadStory).collect { response ->
                _uploadStatus.value = Event(response)
            }
        }
    }
}