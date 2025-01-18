package com.example.moviesmate.presentation.screens.passwordRecover.verifyEmail

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentVerifyEmailBinding
import com.example.moviesmate.presentation.base.BaseFragment

class VerifyEmailFragment :
    BaseFragment<FragmentVerifyEmailBinding>(FragmentVerifyEmailBinding::inflate) {

    override fun viewCreated() {
        goToCreateNewPasswordFragment()
    }

    private fun goToCreateNewPasswordFragment(){
        binding.btnVerify.setOnClickListener {
            findNavController().navigate(VerifyEmailFragmentDirections.actionVerifyEmailFragmentToCreateNewPasswordFragment())
        }
    }
}