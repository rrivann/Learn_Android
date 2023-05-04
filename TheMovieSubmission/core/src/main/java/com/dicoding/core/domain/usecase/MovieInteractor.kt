package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovie(page: Int) = movieRepository.getAllMovie(page)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)
}