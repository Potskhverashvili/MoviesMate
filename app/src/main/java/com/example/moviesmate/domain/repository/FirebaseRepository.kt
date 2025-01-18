package com.example.moviesmate.domain.repository

import com.example.moviesmate.core.OperationStatus
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {
    suspend fun registerNewUser(
        email: String,
        //username: String,
        password: String
    ): OperationStatus<FirebaseUser>
}