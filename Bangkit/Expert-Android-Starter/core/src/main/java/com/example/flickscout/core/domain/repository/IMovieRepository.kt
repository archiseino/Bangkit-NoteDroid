package com.example.flickscout.core.domain.repository

import com.example.flickscout.core.data.Resource
import com.example.flickscout.core.data.source.local.entity.MovieEntity
import com.example.flickscout.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getAllMovie(): Flow<Resource<List<Movie>>>

    suspend fun getFavoriteMovie(): Flow<List<Movie>>

    suspend fun setFavoriteMovie(movie: Movie, newState: Boolean)
}