package com.example.storyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.auth.AuthRepository
import com.example.storyapp.data.auth.RegisterResponse
import com.example.storyapp.data.auth.RequestRegister
import com.example.storyapp.data.base.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {
    private val _registerResult = MutableLiveData<ApiResponse<RegisterResponse>>()
    val registerStatus : LiveData<ApiResponse<RegisterResponse>> get() = _registerResult

    fun register(registerUser: RequestRegister) {
        viewModelScope.launch {
            repository.register(registerUser).collect { response ->
                _registerResult.value = response
            }
        }
    }
}