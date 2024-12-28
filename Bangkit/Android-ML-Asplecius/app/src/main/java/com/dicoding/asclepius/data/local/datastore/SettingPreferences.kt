package com.dicoding.asclepius.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.asclepius.utils.THEME_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet

val Context.datastore : DataStore<Preferences> by preferencesDataStore("settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val themeSettings = booleanPreferencesKey(THEME_SETTINGS)

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {preferences ->
            preferences[themeSettings] ?: false
        }
    }

    suspend fun setThemeSetting(isDarkMode: Boolean) {
        dataStore.edit {preferences ->
            preferences[themeSettings] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var instance : SettingPreferences? = null

        fun getInstance(datastore: DataStore<Preferences>) : SettingPreferences {
            return instance ?: synchronized(this) {
                instance ?: SettingPreferences(datastore)
            }
        }
    }
}