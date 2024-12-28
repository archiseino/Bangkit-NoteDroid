package com.example.flickscout.di

import com.example.flickscout.core.domain.usecase.MovieInteractor
import com.example.flickscout.detail.DetailViewModel
import com.example.flickscout.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<com.example.flickscout.core.domain.usecase.MovieUseCase> {MovieInteractor(get())}
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}