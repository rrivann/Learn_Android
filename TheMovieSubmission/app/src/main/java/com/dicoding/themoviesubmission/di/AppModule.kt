package com.dicoding.themoviesubmission.di

import com.dicoding.core.domain.usecase.MovieInteractor
import com.dicoding.core.domain.usecase.MovieUseCase
import com.dicoding.themoviesubmission.detail.DetailViewModel
import com.dicoding.themoviesubmission.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}