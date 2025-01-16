package com.example.moviesmate.presentation.screens.register

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentRegisterBinding
import com.example.moviesmate.presentation.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun viewCreated() {
        goToLogInPage()
    }

    private fun goToLogInPage() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }


    fun isValidGmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$".toRegex()
        return regex.matches(email)
    }

}