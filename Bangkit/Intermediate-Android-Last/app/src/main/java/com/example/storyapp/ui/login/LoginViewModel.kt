package com.example.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.auth.AuthRepository
import com.example.storyapp.data.auth.LoginResponse
import com.example.storyapp.data.auth.RequestLogin
import com.example.storyapp.data.base.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository, ) : ViewModel() {
    private val _loginResult = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResult : LiveData<ApiResponse<LoginResponse>> get() = _loginResult

    fun login(loginUser: RequestLogin) {
        viewModelScope.launch {
            repository.login(loginUser).collect {response ->
                _loginResult.value = response
            }
        }
    }
}