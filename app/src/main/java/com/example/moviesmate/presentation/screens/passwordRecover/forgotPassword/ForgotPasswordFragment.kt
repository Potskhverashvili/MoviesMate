package com.example.moviesmate.presentation.screens.passwordRecover.forgotPassword

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentForgotPasswordBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {
    private val viewmodel by viewModel<ForgotPasswordViewModel>()

    override fun viewCreated() {
        setListeners()
        setCollectors()
    }

    private fun setListeners() = with(binding) {
        btnSend.setOnClickListener {
            val email = emailAddress.text.toString()
            if (viewmodel.isEmailValid(email)) {
                viewmodel.passwordReset(email = email)
            }else{
                emailAddress.error = "Please enter a valid email"
                return@setOnClickListener
            }
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.isEmailSend.collect {
                findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerifyEmailFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.showError.collect { error ->
                Toast.makeText(
                    requireContext(),
                    "Error: ${error.toString()}", Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.isLoadingState.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
}