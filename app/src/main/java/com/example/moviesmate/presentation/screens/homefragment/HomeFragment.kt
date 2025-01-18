package com.example.moviesmate.presentation.screens.homefragment

import com.example.moviesmate.databinding.FragmentHomeBinding
import com.example.moviesmate.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()


    override fun viewCreated() {
        getUsernameByEmail()
    }


    private fun getUsernameByEmail() {
        // Get the current user's email
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email
            if (email != null) {
                // Query the Firestore 'users' collection for the email
                db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            for (document in documents) {
                                val username = document.getString("username")
                                binding.usename.text = username
                                // Use the username as needed
                            }
                        } else {
                            println("No user found with this email.")
                        }
                    }
                    .addOnFailureListener { exception ->
                        println("Error retrieving username: ${exception.message}")
                    }
            } else {
                println("Current user's email is null.")
            }
        } else {
            println("No authenticated user.")
        }
    }
}