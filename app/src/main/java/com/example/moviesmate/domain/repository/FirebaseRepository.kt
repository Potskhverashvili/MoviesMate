package com.example.moviesmate.domain.repository

import com.example.moviesmate.core.OperationStatus
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {
    // --- Register New user ---
    suspend fun registerNewUser(
        username: String,
        email: String,
        password: String
    ): OperationStatus<FirebaseUser>

    // --- Log In User ---
    suspend fun loginInUser(email: String, password: String): OperationStatus<FirebaseUser>
}