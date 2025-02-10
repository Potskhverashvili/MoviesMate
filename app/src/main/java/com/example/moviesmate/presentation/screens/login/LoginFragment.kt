package com.example.moviesmate.presentation.screens.login

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentLoginBinding
import com.example.moviesmate.core.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel by viewModel<LoginViewModel>()

    override fun viewCreated() {
        setListeners()
        setCollectors()
    }

    private fun setListeners() = with(binding) {
        btnLogIn.setOnClickListener() {
            val email = email.text.toString()
            val password = password.text.toString()
            if (viewModel.isFormValid(email, password)) {
                viewModel.loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }

        tvForgetPassword.setOnClickListener {
            goToForgotPasswordFragment()
        }

        binding.btnRegister.setOnClickListener {
            goToRegisterFragment()
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
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
    }

    private fun goToRegisterFragment() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

}