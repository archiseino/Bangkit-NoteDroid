package com.dicoding.asclepius.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.datastore.SettingPreferences
import com.dicoding.asclepius.repositories.ResultHistoryRepository
import com.dicoding.asclepius.utils.di.Injection
import com.dicoding.asclepius.viewmodel.ResultHistoryViewModel
import com.dicoding.asclepius.viewmodel.SettingViewModel

class ViewModelFactory private constructor(private val resultHistoryRepository: ResultHistoryRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(ResultHistoryViewModel::class.java))
            return ResultHistoryViewModel(resultHistoryRepository) as T
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

class SettingPrefViewModelFactory (private val preferences : SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }

}