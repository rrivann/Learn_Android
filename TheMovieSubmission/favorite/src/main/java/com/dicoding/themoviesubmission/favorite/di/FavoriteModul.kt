package com.dicoding.themoviesubmission.favorite.di

import com.dicoding.themoviesubmission.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}