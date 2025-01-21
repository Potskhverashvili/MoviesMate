package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository

class PasswordResetUseCase(
    private val firebaseRepository: FirebaseRepository

) {
    suspend fun execute(
        email: String
    ): OperationStatus<Unit> {
        return firebaseRepository.passwordReset(email = email)
    }
}