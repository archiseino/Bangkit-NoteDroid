package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsubmissionandroidfundamentalbangkit.data.response.UserDetail
import com.example.projectsubmissionandroidfundamentalbangkit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val username = MutableLiveData<String>()

    private val _user = MutableLiveData<UserDetail?>()
    val user : LiveData<UserDetail?> get() = _user

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading


    fun detailProfile() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.detailProfile(username.value!!)
                _user.value = response.body()
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