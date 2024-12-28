package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.datastore.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val preferences : SettingPreferences) : ViewModel() {

    fun getThemeSettings() : LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun setThemeSettings(isDarkMode : Boolean)  {
        viewModelScope.launch {
            preferences.setThemeSetting(isDarkMode)
        }
    }
}