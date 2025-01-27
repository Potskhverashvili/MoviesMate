package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.repository.MoviesRepository

class ActorDetailsUseCase(
    val repository: MoviesRepository
) {

    suspend fun execute(movieId: Int): OperationStatus<ActorDetails>{
        return repository.getActorDetails(movieId)
    }
}