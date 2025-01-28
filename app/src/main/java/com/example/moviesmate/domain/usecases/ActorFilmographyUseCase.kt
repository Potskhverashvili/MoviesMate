package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.ActorFilmography
import com.example.moviesmate.domain.repository.MoviesRepository

class ActorFilmographyUseCase(
    private val repository: MoviesRepository
) {

    suspend fun execute(id: Int): OperationStatus<ActorFilmography>{
        return repository.getActorFilmography(actorId = id)
    }
}