package com.example.moviesmate.presentation.screens.containerFragment.profile

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.FragmentProfileBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModel<ProfileViewModel>()

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.uploadImageToFireStore(it) // Call ViewModel function
            }
        }

    override fun viewCreated() {
        getUsername()
        getUserEmail()
        setListeners()
        setCollector()
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
            pickImageLauncher.launch("image/*")

        }
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

        lifecycleScope.launch {
            viewModel.selectedImageUri.collect { uri ->
                uri?.let {
                    Glide.with(this@ProfileFragment) // Or use Fragment context
                        .load(it)
                        .into(binding.userImage) // Replace with your ImageView
                }
            }
        }
    }

    private fun getUsername() {
        viewModel.getUsername()
    }

    private fun getUserEmail() {
        viewModel.getUserEmail()
    }
}