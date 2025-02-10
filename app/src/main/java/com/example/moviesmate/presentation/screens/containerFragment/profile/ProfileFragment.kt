package com.example.moviesmate.presentation.screens.containerFragment.profile

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviesmate.R
import com.example.moviesmate.databinding.FragmentProfileBinding
import com.example.moviesmate.core.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModel<ProfileViewModel>()

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.uploadImageToFireStore(it)
            }
        }

    override fun viewCreated() {
        getUsername()
        getUserEmail()
        setListeners()
        setCollector()
        visitDevelopersLinkedin()
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
            activity?.finish()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedImageUri.collect { uri ->
                uri?.let {
                    Glide.with(this@ProfileFragment) // Or use Fragment context
                        .load(it)
                        .into(binding.userImage) // Replace with your ImageView
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun getUsername() {
        viewModel.getUsername()
    }

    private fun getUserEmail() {
        viewModel.getUserEmail()
    }

    private fun visitDevelopersLinkedin() = with(binding) {
        gioDeveloperLinkedIn.setOnClickListener {
            openLinkedIn("https://www.linkedin.com/in/giorgi-dzagania/")
        }
        giaDeveloperLinkedIn.setOnClickListener {
            openLinkedIn("https://www.linkedin.com/in/gia-potskhverashvili/")
        }
    }

    private fun openLinkedIn(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}