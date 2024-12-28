package com.example.flickscout.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,

    val title: String,

    val overview: String,

    val posterPath: String,

    val popularity: Float,

    val releaseDate: String,

    val isFavorite: Boolean
): Parcelable