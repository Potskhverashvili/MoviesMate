package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.repository.MoviesRepository

class FetchMoviesByGenreUseCase(
    private val repository: MoviesRepository
) {

    suspend fun execute(genreId: Int, page: Int): OperationStatus<CategoryMovies> {
        return repository.getMovieByGenre(genreId, page)
    }

}