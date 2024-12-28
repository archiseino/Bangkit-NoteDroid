package com.example.storyapp.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.base.BaseResponse
import com.example.storyapp.utils.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService, private val dataStore: DataStoreManager) : AuthRepository {

    override fun login(user: RequestLogin): Flow<ApiResponse<LoginResponse>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = authService.login(user.email, user.password)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                dataStore.setUserPref(response.loginResult)
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Error occurred"))
        }
    }

    override fun register(user: RequestRegister) : Flow<ApiResponse<RegisterResponse>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = authService.register(user.name, user.email, user.password)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Error occurred"))
        }
    }
}