package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.SearchInput
import com.example.moviesmate.domain.repository.MoviesRepository

class SearchMovieInputUseCase(
    private val repository: MoviesRepository
) {

    suspend fun execute(inputQuery: String): OperationStatus<SearchInput> {
        return repository.getSearchedMovie(query = inputQuery)
    }

}