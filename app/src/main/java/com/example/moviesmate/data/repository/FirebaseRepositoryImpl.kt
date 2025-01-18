package com.example.moviesmate.data.repository

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
        email: String,
        password: String
    ): OperationStatus<FirebaseUser> {
        return FirebaseCallHelper.safeFirebaseCall {

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user ?: throw Exception("User creation failed.")

        }
    }
    /*
        override suspend fun registerNewUser(
            email: String,
            username: String,
            password: String
        ): OperationStatus<Unit> {
            return FirebaseCallHelper.safeFirebaseCall {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Get the registered user ID
                            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                            // Save the username in FireStore
                            val userMap = hashMapOf(
                                "username" to username,
                                "email" to email
                            )
                            firestore.collection("users").document(userId)
                                .set(userMap)
                                .addOnSuccessListener {
                                    Log.d("Register", "User profile saved to Firestore")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Register", "Failed to save user profile", e)
                                }
                        } else {
                            Log.e("Register", "Authentication failed", task.exception)

                        }
                    }
            }*/


    // ----------------- Auth test ---------------------
//    fun registerUser(username: String, email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Get the registered user ID
//                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
//
//                    // Save the username in FireStore
//                    val userMap = hashMapOf(
//                        "username" to username,
//                        "email" to email
//                    )
//                    firestore.collection("users").document(userId)
//                        .set(userMap)
//                        .addOnSuccessListener {
//                            Log.d("Register", "User profile saved to Firestore")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.e("Register", "Failed to save user profile", e)
//                        }
//                } else {
//                    Log.e("Register", "Authentication failed", task.exception)
//                }
//            }
//    }
}
