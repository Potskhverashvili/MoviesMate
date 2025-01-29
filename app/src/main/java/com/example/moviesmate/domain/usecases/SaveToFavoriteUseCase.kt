package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.repository.MoviesRepository

class SaveToFavoriteUseCase(
    private val repository: MoviesRepository
) {
    suspend fun execute(movie: Movie): OperationStatus<Unit> {
        return repository.saveToFavorite(movie)
    }
}