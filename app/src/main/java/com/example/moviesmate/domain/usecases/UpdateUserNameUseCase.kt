package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository

class UpdateUserNameUseCase(
    private val firebaseRepository: FirebaseRepository
) {
    suspend fun execute(updateName: String): OperationStatus<Unit> {
        return firebaseRepository.updateUsername(updateName)
    }
}