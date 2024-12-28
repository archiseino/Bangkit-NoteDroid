package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ResultHistory::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao() : HistoryDao

    companion object {
        @Volatile
        private var instance : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "user.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }

    }

}