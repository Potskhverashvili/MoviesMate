package com.example.moviesmate.presentation.screens.containerFragment.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
            uri?.let { viewModel.uploadImageToFireStore(it) }
        }

    override fun viewCreated() {
        fetchUserProfileImage()
        getUsername()
        getUserEmail()
        visitDevelopersLinkedin()
        setListeners()
        setCollector()
    }

    private fun fetchUserProfileImage() {
        viewModel.fetchUserProfileImage()
    }

    private fun setListeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        updateUsernameButton.setOnClickListener {
            viewModel.updateUserName(usernameEditText.text.toString())
        }

        btnLogOut.setOnClickListener {
            viewModel.logOut()
            val navController = requireActivity().findNavController(R.id.fragmentContainerView)
            navController.popBackStack(R.id.containerFragment, true)
            navController.navigate(R.id.loginFragment)
        }

        btnUpdateImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.imageArrow.setOnClickListener {
            val nestedScrollView = binding.nestedScrollView
            nestedScrollView.smoothScrollTo(0, nestedScrollView.getChildAt(0).height)
        }
    }

    private fun setCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.username.collect {
                    binding.userName.text = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userEmail.collect {
                    binding.userEmail.text = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedImageUri.collect { uri ->
                    Log.d("Check", "Check ${uri}")
                    uri?.let {
                        Glide.with(this@ProfileFragment)
                            .load(it)
                            .into(binding.userImage)
                    }
                }
//            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { errorMessage ->
                    Toast.makeText(requireContext(), "$errorMessage", Toast.LENGTH_SHORT).show()
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

    private fun visitDevelopersLinkedin() = with(binding) {
        gioDeveloperLinkedIn.setOnClickListener { openLinkedIn("https://www.linkedin.com/in/giorgi-dzagania/") }
        giaDeveloperLinkedIn.setOnClickListener { openLinkedIn("https://www.linkedin.com/in/gia-potskhverashvili/") }
    }

    private fun openLinkedIn(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}