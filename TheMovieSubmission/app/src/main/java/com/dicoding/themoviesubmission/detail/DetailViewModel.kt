package com.dicoding.themoviesubmission.detail

import androidx.lifecycle.ViewModel
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)
}