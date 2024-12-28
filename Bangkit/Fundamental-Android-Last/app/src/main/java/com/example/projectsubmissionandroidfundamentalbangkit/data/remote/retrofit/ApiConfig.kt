package com.example.projectsubmissionandroidfundamentalbangkit.data.remote.retrofit

import com.example.projectsubmissionandroidfundamentalbangkit.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private var apiService : ApiService? = null

    fun getApiService(): ApiService {
         if (apiService == null) {
            val authInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", BuildConfig.API_KEY)
                    .build()
                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofitBuilder.create(ApiService::class.java)
        }
        return apiService!!
    }
}