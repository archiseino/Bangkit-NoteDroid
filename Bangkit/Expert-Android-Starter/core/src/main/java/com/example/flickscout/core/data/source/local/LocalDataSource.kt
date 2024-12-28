package com.example.flickscout.core.data.source.local

import com.example.flickscout.core.data.source.local.entity.MovieEntity
import com.example.flickscout.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovie() : Flow<List<MovieEntity>> = movieDao.getListMovie()

    fun getFavoriteMovie() : Flow<List<MovieEntity>> = movieDao.getFavMovie()

    suspend fun insertFavorite(movies: List<MovieEntity>) = movieDao.insertMovieToFav(movies)

    suspend fun setFavoriteMovie(movie: MovieEntity, newStatus: Boolean) {
        movie.isFavorite = newStatus
        movieDao.updateFavMovie(movie)
    }

}