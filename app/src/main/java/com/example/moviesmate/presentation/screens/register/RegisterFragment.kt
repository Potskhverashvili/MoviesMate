package com.example.moviesmate.presentation.screens.register

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentRegisterBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModel<RegisterViewModel>()

    override fun viewCreated() {
        goToLogInPage()
        registerNewUser()
        setCollectors()
    }

    private fun registerNewUser() {
        binding.btnRegister.setOnClickListener {
            val userName = binding.userName.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeatPassword = binding.passwordRepeat.text.toString()
            if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (password != repeatPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            viewModel.registerNewUser(userName, email, password)
        }
    }


    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerFlow.collect {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showError.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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