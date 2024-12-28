package com.example.flickscout.core.utils

import com.example.flickscout.core.data.source.local.entity.MovieEntity
import com.example.flickscout.core.data.source.remote.response.ResultsItem
import com.example.flickscout.core.domain.model.Movie

object DataMapper {

    fun mapResponseToEntities(input: List<ResultsItem>) : List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                popularity = it.popularity,
                releaseDate = it.releaseDate,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input : List<MovieEntity>) : List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                popularity = it.popularity,
                releaseDate = it.releaseDate,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input : Movie) : MovieEntity =
            MovieEntity(
                id = input.id,
                title = input.title,
                overview = input.overview,
                posterPath = input.posterPath,
                popularity = input.popularity,
                releaseDate = input.releaseDate,
                isFavorite = input.isFavorite
            )

}