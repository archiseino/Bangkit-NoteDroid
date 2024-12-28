package com.example.storyapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.DetailResponse
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {
    private val _detailResult = MutableLiveData<Event<ApiResponse<DetailResponse>>>()
    val detailResult : LiveData<Event<ApiResponse<DetailResponse>>> get() = _detailResult

    fun getDetailStory(storyId: String) {
        viewModelScope.launch {
            storyRepository.getDetailStory(storyId).collect {response ->
                _detailResult.value = Event(response)
            }
        }
    }
}