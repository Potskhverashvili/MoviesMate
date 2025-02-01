package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository

class GetUserEmailUseCase(
    private val firebaseRepository: FirebaseRepository
) {
    suspend fun execute(): OperationStatus<String?> {
        return firebaseRepository.getUserEmail()
    }
}