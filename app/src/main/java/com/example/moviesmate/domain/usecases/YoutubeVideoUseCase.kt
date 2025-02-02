package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.Youtube
import com.example.moviesmate.domain.repository.MoviesRepository

class YoutubeVideoUseCase(
    private val repository: MoviesRepository
) {
    suspend fun execute(id: String): OperationStatus<Youtube>{
        return repository.getVideoTrailer(movieId = id)
    }
}