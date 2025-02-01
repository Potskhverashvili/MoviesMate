package com.example.moviesmate.presentation.screens.containerFragment.profile

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesmate.databinding.FragmentProfileBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModel<ProfileViewModel>()

    override fun viewCreated() {
        setListeners()

        setCollector()
        getUsername()
        getUserEmail()
    }


    private fun setCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.username.collect {
                binding.userName.text = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userEmail.collect {
                binding.userEmail.text = it
            }
        }
    }

    private fun setListeners() = with(binding) {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        updateUsernameButton.setOnClickListener {
            viewModel.updateUserName(usernameEditText.text.toString())
        }

        btnLogOut.setOnClickListener {
            viewModel.logOut()
            requireActivity().finish()
        }


        binding.btnUpdateImage.setOnClickListener {

        }

    }


    private fun getUsername() {
        viewModel.getUsername()
    }

    private fun getUserEmail() {
        viewModel.getUserEmail()
    }
}