package com.example.moviesmate.presentation.screens.containerFragment.profile

import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentProfileBinding
import com.example.moviesmate.presentation.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun viewCreated() {
        setListeners()
    }

    private fun setListeners() = with(binding) {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}