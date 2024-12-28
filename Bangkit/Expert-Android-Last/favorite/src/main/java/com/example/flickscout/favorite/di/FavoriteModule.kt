package com.example.flickscout.favorite.di

import com.example.flickscout.favorite.detail.DetailFavoriteViewModel
import com.example.flickscout.favorite.ui.FavoriteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { DetailFavoriteViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}