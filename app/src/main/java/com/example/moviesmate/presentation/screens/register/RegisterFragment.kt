package com.example.moviesmate.presentation.screens.register

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentRegisterBinding
import com.example.moviesmate.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun viewCreated() {
        goToLogInPage()
        registerUser("user1", "user1@gmail.com", "12341234")

    }


    // ----------------- Auth test ---------------------
    fun registerUser(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get the registered user ID
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    // Save the username in Firestore
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
    }


    // --- Go to Login Page ---
    private fun goToLogInPage() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }


    // --- Email Validation ----
    fun isValidGmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$".toRegex()
        return regex.matches(email)
    }



}