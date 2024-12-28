package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(resultHistory : ResultHistory)

    @Query("SELECT * FROM HISTORY")
    fun getHistory(): LiveData<List<ResultHistory>>
}