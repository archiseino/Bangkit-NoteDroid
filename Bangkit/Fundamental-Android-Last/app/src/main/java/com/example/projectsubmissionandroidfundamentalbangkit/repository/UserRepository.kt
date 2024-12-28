package com.example.projectsubmissionandroidfundamentalbangkit.repository

import androidx.lifecycle.LiveData
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.database.UserDAO
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity.UserEntity
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.ResponseApi
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.retrofit.ApiService
import retrofit2.Response

class UserRepository(
    private val apiService : ApiService,
    private val userDao : UserDAO
) {

    fun getFavorites() : LiveData<List<UserEntity>> {
        return userDao.getFavorites()
    }

    suspend fun isUserFavorite(user: String) : Int {
        return userDao.isUserBookmarked(user)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: String) {
        userDao.deleteUser(user)
    }

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDAO
        ) : UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, userDao)
        }.also { instance = it }
    }
}