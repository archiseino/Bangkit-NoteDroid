package com.example.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.auth.LoginResult
import com.example.storyapp.data.stories.ListStoryItem
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: StoryRepository, private val dataStore: DataStoreManager) : ViewModel() {
    private val _storiesResult = MutableLiveData<PagingData<ListStoryItem>>()
    val storiesResult : LiveData<PagingData<ListStoryItem>> get() = _storiesResult

    private val _user = MutableLiveData<LoginResult>()
    val user : LiveData<LoginResult> get() = _user

    fun getAllStories() {
        viewModelScope.launch {
            repository.getStories().cachedIn(viewModelScope).observeForever { result ->
                _storiesResult.value = result
            }
        }
    }

    fun getUserPref() {
        viewModelScope.launch {
            dataStore.getUserPref().collect { result ->
                _user.value = result
            }
        }
    }
}