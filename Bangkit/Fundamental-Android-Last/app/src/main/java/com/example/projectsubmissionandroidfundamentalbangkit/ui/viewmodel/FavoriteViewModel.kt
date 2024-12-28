package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity.UserEntity
import com.example.projectsubmissionandroidfundamentalbangkit.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository): ViewModel() {
     fun getFavorites() : LiveData<List<UserEntity>> = userRepository.getFavorites()
}