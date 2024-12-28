package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class NewsViewModel() : ViewModel() {

    private val _news = MutableLiveData<List<ArticlesItem?>>()
    val news : LiveData<List<ArticlesItem?>> get() = _news

    private val _loadStatus = MutableLiveData<Boolean>()
    val loadStatus : LiveData<Boolean> get() = _loadStatus

    fun getNews() {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().getNews()
            if (response.isSuccessful) {
                _news.value = response.body()?.articles!!
            }
        }
    }


}