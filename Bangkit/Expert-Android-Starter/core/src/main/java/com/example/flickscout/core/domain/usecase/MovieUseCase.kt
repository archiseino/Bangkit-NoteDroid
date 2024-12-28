package com.example.flickscout.core.domain.usecase

import com.example.flickscout.core.data.Resource
import com.example.flickscout.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    suspend fun getMovies() : Flow<Resource<List<Movie>>>

    suspend fun getFavoriteMovies() : Flow<List<Movie>>

    suspend fun setFavoriteMovie(movie: Movie, newState: Boolean)
}