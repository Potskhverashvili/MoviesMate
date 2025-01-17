package com.example.moviesmate.presentation.screens.passwordRecover.createNewPassword

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentCreateNewPasswordBinding
import com.example.moviesmate.presentation.base.BaseFragment

class CreateNewPasswordFragment :
    BaseFragment<FragmentCreateNewPasswordBinding>(FragmentCreateNewPasswordBinding::inflate) {

    override fun viewCreated() {
        goBackToLogInFragment()
    }

    private fun goBackToLogInFragment() {
        binding.btnSave.setOnClickListener {
            findNavController().navigate(CreateNewPasswordFragmentDirections.actionCreateNewPasswordFragmentToLoginFragment())
        }
    }

}