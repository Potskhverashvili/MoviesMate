package com.example.moviesmate.presentation.screens.register

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.core.base.BaseFragment
import com.example.moviesmate.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModel<RegisterViewModel>()

    override fun viewCreated() {
        setListener()
        setCollectors()
    }

    private fun setListener() {
        binding.btnRegister.setOnClickListener {
            val userName = binding.userName.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeatPassword = binding.passwordRepeat.text.toString()
            val isValid = viewModel.isRegistrationValid(userName, email, password, repeatPassword)
            if (isValid) {
                viewModel.registerNewUser(userName, email, password)
            }
        }

        binding.btnLogIn.setOnClickListener {
            goToLogInPage()
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerFlow.collect {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToContainerFragment())
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingState.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

    }

    private fun goToLogInPage() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }
}