package com.example.flickscout.core.domain.usecase

import com.example.flickscout.core.data.Resource
import com.example.flickscout.core.domain.model.Movie
import com.example.flickscout.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val repository: IMovieRepository) :
    com.example.flickscout.core.domain.usecase.MovieUseCase {
    override suspend fun getMovies(): Flow<Resource<List<Movie>>> {
        return repository.getAllMovie()
    }

    override  suspend fun getFavoriteMovies(): Flow<List<Movie>> {
        return repository.getFavoriteMovie()
    }

    override suspend fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        repository.setFavoriteMovie(movie, newState)
    }
}