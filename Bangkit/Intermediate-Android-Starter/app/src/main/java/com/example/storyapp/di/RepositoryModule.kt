package com.example.storyapp.di

import com.example.storyapp.data.auth.AuthRepository
import com.example.storyapp.data.auth.AuthRepositoryImpl
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.data.stories.StoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteRepositoryModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun bindStoryRepository(impl: StoryRepositoryImpl) : StoryRepository
}