package com.example.moviesmate.presentation.screens.login

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentLoginBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel by viewModel<LoginViewModel>()

    override fun viewCreated() {
        logInUser()
        setCollectors()
        goToRegisterFragment()
        goToForgotPasswordFragment()
    }

    // --- Login User ---
    private fun logInUser() = with(binding) {
        btnLogIn.setOnClickListener() {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
    }

    // --- Set collectors ---
    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginFlow.collect {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToContainerFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showError.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
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


}