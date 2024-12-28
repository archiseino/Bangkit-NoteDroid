package com.example.flickscout.core.data.source.remote.network

import com.example.flickscout.core.data.source.remote.response.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("language") language: String = "us",
        @Query("sort_by") sortBy : String= "popularity.desc"
    ) : com.example.flickscout.core.data.source.remote.response.MoviesResponse
}