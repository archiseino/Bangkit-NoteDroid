package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsubmissionandroidfundamentalbangkit.data.response.UserResponse
import com.example.projectsubmissionandroidfundamentalbangkit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isFound = MutableLiveData(true)
    val isFound : LiveData<Boolean> = _isFound

    private val _usersResult = MutableLiveData<List<UserResponse?>>()
    val usersResult : LiveData<List<UserResponse?>> get() = _usersResult

    fun searchUserResult(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.searchProfile(query)
                _usersResult.postValue(response.body()?.items!!)
                _isFound.value = response.body()?.items!!.isNotEmpty()
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
            }
        }

    }

}