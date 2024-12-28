package com.example.storyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storyapp.data.stories.ListStoryItem

@Database(
    entities = [ListStoryItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun remoteKeyDao() : RemoteKeysDao
    abstract fun storyDao() : StoryItemDao
}