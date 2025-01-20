package com.example.moviesmate.presentation.screens.containerFragment.home

import android.widget.Toast
import com.example.moviesmate.databinding.FragmentHomeBinding
import com.example.moviesmate.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun viewCreated() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        binding.usename.text = username
                    }
                }
        }else{
            Toast.makeText(requireContext(), "UserName", Toast.LENGTH_SHORT).show()
        }
    }

}