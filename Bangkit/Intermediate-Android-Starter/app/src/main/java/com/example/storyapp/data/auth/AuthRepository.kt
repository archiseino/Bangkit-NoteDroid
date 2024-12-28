package com.example.storyapp.data.auth

import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.base.BaseResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(user: RequestLogin) : Flow<ApiResponse<LoginResponse>>
    fun register(user: RequestRegister) : Flow<ApiResponse<RegisterResponse>>
}