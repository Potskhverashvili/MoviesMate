package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.repository.MoviesRepository

class GetAllFavoritesUseCase(
    private val repository: MoviesRepository
) {
    suspend fun execute(): OperationStatus<List<Movie>> {
        return repository.getAllSavedMovies()
    }
}