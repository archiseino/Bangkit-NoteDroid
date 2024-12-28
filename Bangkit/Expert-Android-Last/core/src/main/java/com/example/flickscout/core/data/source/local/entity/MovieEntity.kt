package com.example.flickscout.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,

    val title: String,

    val overview: String,

    val posterPath: String,

    val popularity: Float,

    val releaseDate: String,

    var isFavorite: Boolean = false


)