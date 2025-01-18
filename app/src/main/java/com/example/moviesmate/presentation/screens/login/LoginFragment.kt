package com.example.moviesmate.presentation.screens.login

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentLoginBinding
import com.example.moviesmate.presentation.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun viewCreated() {

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

    }

    fun isValidGmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$".toRegex()
        return regex.matches(email)
    }

}