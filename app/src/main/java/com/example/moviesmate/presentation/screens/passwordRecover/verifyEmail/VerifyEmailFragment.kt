package com.example.moviesmate.presentation.screens.passwordRecover.verifyEmail

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentVerifyEmailBinding
import com.example.moviesmate.core.base.BaseFragment

class VerifyEmailFragment :
    BaseFragment<FragmentVerifyEmailBinding>(FragmentVerifyEmailBinding::inflate) {

    override fun viewCreated() {
        setListeners()
    }

    private fun setListeners() = with(binding) {
        btnLogInPage.setOnClickListener {
            findNavController().navigate(VerifyEmailFragmentDirections.actionVerifyEmailFragmentToLoginFragment())
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}