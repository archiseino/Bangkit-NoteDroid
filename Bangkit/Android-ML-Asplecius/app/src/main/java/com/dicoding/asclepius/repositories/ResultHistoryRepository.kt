package com.dicoding.asclepius.repositories

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.room.HistoryDao
import com.dicoding.asclepius.data.local.room.ResultHistory

class ResultHistoryRepository (private val historyDao : HistoryDao) {
    suspend fun insertHistory(userHistory : ResultHistory) {
        historyDao.insertHistory(userHistory)
    }

    fun getHistory() : LiveData<List<ResultHistory>> {

        return historyDao.getHistory()
    }

    companion object {
        @Volatile
        private var instance : ResultHistoryRepository? = null
        fun getInstance(
            history: HistoryDao
        ) : ResultHistoryRepository = instance ?: synchronized(this) {
            instance ?: ResultHistoryRepository(history)
        }.also { instance = it }
    }
}