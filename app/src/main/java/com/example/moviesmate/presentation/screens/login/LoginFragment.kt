package com.example.moviesmate.presentation.screens.login

import com.example.moviesmate.databinding.FragmentLoginBinding
import com.example.moviesmate.presentation.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun viewCreated() {

    }

    fun isValidGmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$".toRegex()
        return regex.matches(email)
    }
    

    // -- Go to register page --


}