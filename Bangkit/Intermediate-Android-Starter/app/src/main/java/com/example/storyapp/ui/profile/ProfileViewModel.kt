package com.example.storyapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.auth.LoginResult
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val dataStore: DataStoreManager) : ViewModel() {

    private val _user = MutableLiveData<LoginResult>()
    val user: LiveData<LoginResult> get() = _user

    fun getUserPref() {
        viewModelScope.launch {
            dataStore.getUserPref().collect { result ->
                _user.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.userLogout()
        }
    }
}
