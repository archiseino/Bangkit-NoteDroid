package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.UserResponse
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class FollowingViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val username = MutableLiveData<String>()

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _followingUser = MutableLiveData<List<UserResponse?>>()
    val followingUser : LiveData<List<UserResponse?>> get() = _followingUser


    fun getFollowing() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.userFollowing(username.value!!)
                println(response)
                _followingUser.value = response.body()
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }

    fun setUsername(string: String) {
        username.value = string
    }

}