package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser

class RegisterNewUserUseCase(
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun execute(
        username: String, email: String, password: String
    ): OperationStatus<FirebaseUser> {
        return firebaseRepository.registerNewUser(username, email, password)
    }
}