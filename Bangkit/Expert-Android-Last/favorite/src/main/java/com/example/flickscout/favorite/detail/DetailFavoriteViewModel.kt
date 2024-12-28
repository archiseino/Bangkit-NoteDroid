package com.example.flickscout.favorite.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickscout.core.domain.model.Movie
import com.example.flickscout.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailFavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        viewModelScope.launch {
            movieUseCase.setFavoriteMovie(movie, newState)
        }
    }
}