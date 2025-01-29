package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.UpcomingMovies
import com.example.moviesmate.domain.repository.MoviesRepository

class UpcomingMoviesUseCase(
    private val repository: MoviesRepository
) {

    suspend fun execute():OperationStatus<UpcomingMovies>{
        return repository.getUpcomingMovies()
    }
}