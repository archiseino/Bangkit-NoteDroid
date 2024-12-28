package com.example.storyapp.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyapp.data.auth.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val usernamePref = stringPreferencesKey("username")
    private val tokenPref = stringPreferencesKey("token")
    private val usernameId = stringPreferencesKey("userId")

    suspend fun setUserPref(user: LoginResult) {
        dataStore.edit { userPref ->
            userPref[usernamePref] = user.name
            userPref[tokenPref] = user.token
            userPref[usernameId] = user.userId
        }
    }

    suspend fun getUserToken() : String? =
        dataStore.data.first()[tokenPref]

    fun getUserPref() : Flow<LoginResult> {
        return dataStore.data.map {preferences ->
            LoginResult(
                name = preferences[usernamePref] ?: "",
                userId = preferences[usernameId] ?: "",
                token = preferences[tokenPref] ?: ""
            )
        }
    }

    suspend fun userLogout() {
        dataStore.edit { userPref ->
            userPref.clear()
        }
    }

}