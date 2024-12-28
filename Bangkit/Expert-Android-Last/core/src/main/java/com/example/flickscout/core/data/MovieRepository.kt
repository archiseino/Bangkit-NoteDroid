package com.example.flickscout.core.data

import com.example.flickscout.core.data.source.local.LocalDataSource
import com.example.flickscout.core.data.source.remote.RemoteDataSource
import com.example.flickscout.core.data.source.remote.network.ApiResponse
import com.example.flickscout.core.data.source.remote.response.ResultsItem
import com.example.flickscout.core.domain.model.Movie
import com.example.flickscout.core.domain.repository.IMovieRepository
import com.example.flickscout.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository{

    override suspend fun getAllMovie(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertFavorite(movieList)
            }

        }.asFlow()
    }

    override suspend fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        localDataSource.setFavoriteMovie(movieEntity, newState)

    }
}