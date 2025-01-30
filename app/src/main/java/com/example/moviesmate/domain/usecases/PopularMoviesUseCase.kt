package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.HomePageMovies
import com.example.moviesmate.domain.repository.MoviesRepository

class PopularMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend fun execute(): OperationStatus<HomePageMovies> {
        return repository.getPopularMovies()
    }
}