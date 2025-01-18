package com.example.moviesmate.data.repository

import android.util.Log
import com.example.moviesmate.core.FirebaseCallHelper
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl(
    private val auth: FirebaseAuth
) : FirebaseRepository {

    override suspend fun registerNewUser(
        email: String,
        password: String
    ): OperationStatus<FirebaseUser> {
        return FirebaseCallHelper.safeFirebaseCall {
            val result = auth.createUserWithEmailAndPassword(email,password).await()
            Log.d("FirebaseRepositoryImpl","result => ${result}")
            result.user ?: throw Exception("User creation failed.")
        }
    }


}