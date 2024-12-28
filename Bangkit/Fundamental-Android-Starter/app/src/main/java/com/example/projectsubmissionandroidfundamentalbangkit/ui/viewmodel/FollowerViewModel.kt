package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsubmissionandroidfundamentalbangkit.data.response.UserResponse
import com.example.projectsubmissionandroidfundamentalbangkit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class FollowerViewModel : ViewModel() {

    private val apiService = ApiConfig.getApiService()

    private val username = MutableLiveData<String>()

    private val _isLoading = MutableLiveData(true)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _followerUser = MutableLiveData<List<UserResponse?>>()
    val followerUser : LiveData<List<UserResponse?>> get() = _followerUser


    fun getFollower() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.userFollower(username.value!!)
                _followerUser.value = response?.body()
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