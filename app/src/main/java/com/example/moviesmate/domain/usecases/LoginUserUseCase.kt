package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser

class LoginUserUseCase(
    private val firebaseRepository: FirebaseRepository
) {
    suspend fun execute(
        email: String, password: String
    ): OperationStatus<FirebaseUser> {
        return firebaseRepository.loginInUser(email, password)
    }
}