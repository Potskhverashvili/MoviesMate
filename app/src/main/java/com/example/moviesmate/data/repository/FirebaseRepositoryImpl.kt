package com.example.moviesmate.data.repository

import android.util.Log.d
import com.example.moviesmate.core.FirebaseCallHelper
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseRepository {

    override suspend fun registerNewUser(
        username: String,
        email: String,
        password: String
    ): OperationStatus<FirebaseUser> {
        return FirebaseCallHelper.safeFirebaseCall {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if (user != null) {
                val userMap = hashMapOf(
                    "username" to username,
                    "email" to email
                )
                firestore.collection("users").document(user.uid).set(userMap).await()
            }
            user!! // Return the user (non-null assertion is safe here)
        }
    }
}