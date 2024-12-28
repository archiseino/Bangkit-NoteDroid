package com.example.flickscout.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickscout.core.domain.model.Movie
import kotlinx.coroutines.launch

class FavoriteViewModel(private val movieUseCase: com.example.flickscout.core.domain.usecase.MovieUseCase) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies : LiveData<List<Movie>> get() = _movies

    fun getFavoriteMovies() {
        viewModelScope.launch {
            movieUseCase.getFavoriteMovies().collect {
                _movies.value = it
            }
        }
    }

}