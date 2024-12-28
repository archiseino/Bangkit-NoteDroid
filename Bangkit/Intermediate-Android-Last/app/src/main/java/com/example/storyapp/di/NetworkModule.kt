package com.example.storyapp.di

import com.example.storyapp.BuildConfig
import com.example.storyapp.data.auth.AuthService
import com.example.storyapp.data.stories.StoryService
import com.example.storyapp.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() : String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(dataStoreManager: DataStoreManager) : OkHttpClient {
        val headerInjector = Interceptor { chain ->
            return@Interceptor chain.proceed(
                chain.request()
                    .newBuilder()
                    .header(
                        "Authorization",
                        "Bearer ${runBlocking { dataStoreManager.getUserToken()}}"
                    ).build()
            )
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(headerInjector)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit) : AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideStoryService(retrofit: Retrofit) : StoryService = retrofit.create(StoryService::class.java)
}