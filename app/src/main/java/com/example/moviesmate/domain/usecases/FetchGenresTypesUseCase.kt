package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.repository.MoviesRepository

class FetchGenresTypesUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend fun execute(): OperationStatus<GenresType> {
        return moviesRepository.getGenresTypes()
    }
}