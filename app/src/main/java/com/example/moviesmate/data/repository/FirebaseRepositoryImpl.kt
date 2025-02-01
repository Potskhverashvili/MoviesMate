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
        username: String,
        email: String,
        password: String
    ): OperationStatus<FirebaseUser> {
        return FirebaseCallHelper.safeFirebaseCall {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user

            val userMap = hashMapOf(
                "username" to username,
                "email" to email,
            )
            firestore.collection("users").document(user!!.uid).set(userMap).await()
            user
        }
    }

    override suspend fun loginInUser(
        email: String,
        password: String
    ): OperationStatus<FirebaseUser> {
        return FirebaseCallHelper.safeFirebaseCall {
            val resultUser = auth.signInWithEmailAndPassword(email, password).await()
            resultUser.user!!
        }
    }

    override suspend fun passwordReset(email: String): OperationStatus<Unit> {
        return FirebaseCallHelper.safeFirebaseCall {
            auth.sendPasswordResetEmail(email).await()
        }
    }

    override suspend fun getUsername(): OperationStatus<String> {
        return FirebaseCallHelper.safeFirebaseCall {
            val userId = auth.currentUser?.uid
                ?: throw IllegalStateException("User not authenticated")

            val document = firestore.collection("users").document(userId).get().await()

            if (document.exists()) {
                document.getString("username") ?: throw IllegalStateException("Username not found")
            } else {
                throw IllegalStateException("User document not found")
            }
        }
    }

    override suspend fun updateUsername(updateName: String): OperationStatus<Unit> {
        return FirebaseCallHelper.safeFirebaseCall {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val userRef = userId?.let {
                FirebaseFirestore.getInstance().collection("users").document(
                    it
                )
            }
            userRef?.update("username", updateName)
        }
    }

    override suspend fun getUserEmail(): OperationStatus<String?> {
        return FirebaseCallHelper.safeFirebaseCall {
            auth.currentUser?.email
        }
    }

    override suspend fun logOut(): OperationStatus<Unit> {
        return FirebaseCallHelper.safeFirebaseCall {
            auth.signOut()
        }
    }
}