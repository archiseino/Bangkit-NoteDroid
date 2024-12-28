package com.example.projectsubmissionandroidfundamentalbangkit.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.datastore.SettingPreferences
import com.example.projectsubmissionandroidfundamentalbangkit.utils.di.Injection
import com.example.projectsubmissionandroidfundamentalbangkit.repository.UserRepository
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.DetailViewModel
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.FavoriteViewModel
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.SettingViewModel

class ViewModelFactory private constructor (private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        } else if  (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        private var instance : ViewModelFactory? = null
        fun getInstance(context: Context) : ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}

class SettingPrefViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}