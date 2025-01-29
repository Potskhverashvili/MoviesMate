package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.repository.MoviesRepository

class DeleteFromFavoritesUsaCase(
    private val repository: MoviesRepository
) {
    suspend fun execute(movie: Movie): OperationStatus<Unit> {
        return repository.deleteFromFavorite(movie)
    }
}