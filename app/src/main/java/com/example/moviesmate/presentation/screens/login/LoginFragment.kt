package com.example.moviesmate.presentation.screens.login

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
            }
        }

        tvForgetPassword.setOnClickListener {
            goToForgotPasswordFragment()
        }

        binding.btnRegister.setOnClickListener {
            goToRegisterFragment()
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginFlow.collect {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToContainerFragment())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingState.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showError.collect { errorMessage ->
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