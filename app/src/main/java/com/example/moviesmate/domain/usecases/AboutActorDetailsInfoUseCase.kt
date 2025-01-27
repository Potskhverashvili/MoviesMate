package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.AboutActor
import com.example.moviesmate.domain.repository.MoviesRepository

class AboutActorDetailsInfoUseCase(
    val repository: MoviesRepository
) {
    suspend fun execute(actorId: Int): OperationStatus<AboutActor> {
        return repository.infoAboutActor(actorId = actorId)
    }
}