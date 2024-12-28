package com.example.storyapp.di

import android.content.Context
import androidx.room.Room
import com.example.storyapp.data.local.AppDatabase
import com.example.storyapp.data.local.RemoteKeysDao
import com.example.storyapp.data.local.StoryItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "story.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideStoryDao(appDatabase: AppDatabase) : StoryItemDao {
        return appDatabase.storyDao()
    }

    @Provides
    fun provideRemoteKeysDao(appDatabase: AppDatabase) : RemoteKeysDao {
        return appDatabase.remoteKeyDao()
    }

}