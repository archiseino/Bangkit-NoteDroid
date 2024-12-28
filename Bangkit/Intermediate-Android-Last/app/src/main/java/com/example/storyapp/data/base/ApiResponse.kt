package com.example.storyapp.data.base

sealed class ApiResponse<out R>{
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: String) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}