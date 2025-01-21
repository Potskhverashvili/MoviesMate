package com.example.moviesmate.presentation.screens.passwordRecover.forgotPassword

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentForgotPasswordBinding
import com.example.moviesmate.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    override fun viewCreated() {
        goToVerifyEmailFragment()

        resetPasswordTest("gia.focxverashvili@gmail.com")
    }

    private fun resetPasswordTest(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
    }


    private fun goToVerifyEmailFragment() {
        binding.btnSend.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerifyEmailFragment())
        }
    }
}