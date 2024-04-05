package com.example.projectsubmissionandroidfundamentalbangkit.utils.di

import android.content.Context
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.database.AppDatabase
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.retrofit.ApiConfig
import com.example.projectsubmissionandroidfundamentalbangkit.repository.UserRepository

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val apiService = ApiConfig.getApiService()
        val userDatabase = AppDatabase.getInstance(context)
        val userDao = userDatabase.userDao()
        return UserRepository.getInstance(apiService, userDao)
    }
}