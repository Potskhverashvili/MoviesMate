package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.repository.MoviesRepository

class SearchedCategoryUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun execute(): OperationStatus<CategoryMovies> {
        return moviesRepository.getCategoryMovies()
    }
}