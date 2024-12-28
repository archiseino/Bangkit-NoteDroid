package com.dicoding.asclepius.utils.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.AppDatabase
import com.dicoding.asclepius.data.local.room.HistoryDao
import com.dicoding.asclepius.repositories.ResultHistoryRepository

object Injection {
    fun provideRepository(context: Context) : ResultHistoryRepository {
        val historyDatabase = AppDatabase.getInstance(context)
        val historyDao = historyDatabase.resultDao()
        return ResultHistoryRepository.getInstance(historyDao)
    }
}