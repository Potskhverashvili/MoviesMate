package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.repository.MoviesRepository

class MovieDetailsUseCase(
    private val repository: MoviesRepository
) {

    suspend fun execute(movieId: Int): OperationStatus<MovieDetails>{
        return repository.getMovieDetails(movieId = movieId.toString())
    }
}