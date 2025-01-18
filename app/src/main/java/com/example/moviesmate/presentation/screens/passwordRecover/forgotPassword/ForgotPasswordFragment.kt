package com.example.moviesmate.presentation.screens.passwordRecover.forgotPassword

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentForgotPasswordBinding
import com.example.moviesmate.presentation.base.BaseFragment

class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    override fun viewCreated() {
        goToVerifyEmailFragment()
    }

    private fun goToVerifyEmailFragment(){
        binding.btnSend.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerifyEmailFragment())
        }
    }

}