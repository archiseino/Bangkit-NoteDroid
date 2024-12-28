package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.room.ResultHistory
import com.dicoding.asclepius.repositories.ResultHistoryRepository
import kotlinx.coroutines.launch

class ResultHistoryViewModel(private val repository : ResultHistoryRepository) : ViewModel() {

    fun getHistory(): LiveData<List<ResultHistory>> = repository.getHistory()

    fun insertResultHistory(resultHistory: ResultHistory) {
        viewModelScope.launch {
            repository.insertHistory(resultHistory)
        }
    }
}