package com.example.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.auth.LoginResponse
import com.example.storyapp.data.auth.LoginResult
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.ListStoryItem
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.data.stories.StoryResponse
import com.example.storyapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: StoryRepository, private val dataStore: DataStoreManager) : ViewModel() {
    private val _storiesResult = MutableLiveData<ApiResponse<StoryResponse>>()
    val storiesResult : LiveData<ApiResponse<StoryResponse>> get() = _storiesResult

    private val _user = MutableLiveData<LoginResult>()
    val user : LiveData<LoginResult> get() = _user

    init {
        getStories()
        getUserPref()
    }

    private fun getStories() {
        viewModelScope.launch {
            repository.getStories().collect {response ->
                _storiesResult.value = response
            }
        }
    }

    private fun getUserPref() {
        viewModelScope.launch {
            dataStore.getUserPref().collect { result ->
                _user.value = result
            }
        }
    }
}