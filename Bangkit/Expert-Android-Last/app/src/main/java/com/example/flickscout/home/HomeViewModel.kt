package com.example.flickscout.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickscout.core.data.Resource
import com.example.flickscout.core.domain.model.Movie
import com.example.flickscout.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies : LiveData<Resource<List<Movie>>> get() = _movies


    fun getAllMovies() {
        viewModelScope.launch {
            movieUseCase.getMovies().collect {
                _movies.value = it
            }
        }
    }

}