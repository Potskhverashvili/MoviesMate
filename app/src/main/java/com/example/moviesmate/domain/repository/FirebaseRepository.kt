package com.example.moviesmate.domain.repository

import android.net.Uri
import com.example.moviesmate.core.OperationStatus
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun registerNewUser(
        username: String,
        email: String,
        password: String
    ): OperationStatus<FirebaseUser>

    suspend fun loginInUser(email: String, password: String): OperationStatus<FirebaseUser>
    suspend fun passwordReset(email: String): OperationStatus<Unit>
    suspend fun getUsername(): OperationStatus<String>

    suspend fun updateUsername(updateName: String): OperationStatus<Unit>

    suspend fun getUserEmail(): OperationStatus<String?>

    suspend fun logOut(): OperationStatus<Unit>

    suspend fun uploadImageToFireStore(uri: Uri): OperationStatus<String>
    suspend fun getUserProfileImage(): OperationStatus<String>
}