package com.example.flickscout.core.di

import androidx.room.Room
import com.example.flickscout.core.data.MovieRepository
import com.example.flickscout.core.data.source.local.LocalDataSource
import com.example.flickscout.core.data.source.local.entity.MovieEntity
import com.example.flickscout.core.data.source.local.room.MovieDatabase
import com.example.flickscout.core.data.source.remote.RemoteDataSource
import com.example.flickscout.core.data.source.remote.network.ApiService
import com.example.flickscout.core.domain.repository.IMovieRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movie.db",
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {

    val headerInterceptor = Interceptor {chain ->
            return@Interceptor chain.proceed(
                chain.request()
                    .newBuilder()
                    .header(
                        "Authorization",
                        "Bearer " +"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNGEwMjQ4YTdiYmIzMjdjMzdjM2M4Mjc0YjQ5YWUwMCIsIm5iZiI6MTcyODQzNzcwOS41MDQ1NTMsInN1YiI6IjY3MDVkYzNiMzczYjFiZTBlMGU5ZGUzZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lXYuF4t8GL8dO7BB-dYKTnPUNPi1nn4et5DucYQkOnk"
                    ).build()
            )
        }

    single {
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .build()
        retrofit.create(com.example.flickscout.core.data.source.remote.network.ApiService::class.java)
    }

}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IMovieRepository> {MovieRepository(get(), get())}
}