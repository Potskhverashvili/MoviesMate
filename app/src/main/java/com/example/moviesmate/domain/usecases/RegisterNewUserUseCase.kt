package com.example.moviesmate.domain.usecases

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser

class RegisterNewUserUseCase(
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun execute(
        username: String, email: String, password: String
    ): OperationStatus<FirebaseUser> {
        return try {
            firebaseRepository.registerNewUser(username, email, password)
        } catch (exception: FirebaseAuthUserCollisionException) {
            OperationStatus.Failure(exception)
        } catch (exception: Exception) {
            OperationStatus.Failure(exception)
        }
    }


}