package com.example.moviesmate.presentation.screens.login

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentLoginBinding
import com.example.moviesmate.presentation.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun viewCreated() {
<<<<<<< HEAD
=======
        goToRegisterFragment()
        goToForgotPasswordFragment()
    }

    private fun goToForgotPasswordFragment() {
        binding.tvForgetPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
    }

    private fun goToRegisterFragment() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
>>>>>>> 017db7772e1a07a92ff54c59e1273aa4b46361ff
    }

    fun isValidGmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$".toRegex()
        return regex.matches(email)
    }
<<<<<<< HEAD
=======


>>>>>>> 017db7772e1a07a92ff54c59e1273aa4b46361ff
}