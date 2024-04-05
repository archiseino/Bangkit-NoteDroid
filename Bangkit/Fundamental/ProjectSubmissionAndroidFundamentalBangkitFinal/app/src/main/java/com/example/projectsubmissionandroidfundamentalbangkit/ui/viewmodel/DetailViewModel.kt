package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity.UserEntity
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.UserDetail
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.retrofit.ApiConfig
import com.example.projectsubmissionandroidfundamentalbangkit.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val username = MutableLiveData<String>()

    private val _user = MutableLiveData<UserDetail?>()
    val user : LiveData<UserDetail?> get() = _user

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> get () = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite : LiveData<Boolean> get() = _isFavorite

    fun isFavoritesUser(user: String) {
        viewModelScope.launch {
            val result = userRepository.isUserFavorite(user)
            _isFavorite.value = result == 1
        }
    }

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(user: String) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

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