package com.example.moviesmate.presentation.screens.containerFragment.actorBiography

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesmate.R
import com.example.moviesmate.databinding.FragmentActorBiographyBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActorBiographyFragment :
    BaseFragment<FragmentActorBiographyBinding>(FragmentActorBiographyBinding::inflate) {
    private val args: ActorBiographyFragmentArgs by navArgs()
    private val actorFilmographyAdapter = ActorFilmographyAdapter()
    private val viewModel by viewModel<ActorBiographyViewModel>()

    override fun viewCreated() {
        prepareRecyclerView()
        getActorDetails()
        getActorFilmography()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerView() {
        binding.actorFilmographyRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorFilmographyAdapter
        }
    }

    private fun getActorFilmography() {
        viewModel.getActorFilmography(args.actorId)
    }

    private fun getActorDetails() {
        viewModel.getInfoAboutActor(args.actorId)
    }

    private fun setListeners() {
        actorFilmographyAdapter.onItemClick = { movieId ->
            findNavController().navigate(
                ActorBiographyFragmentDirections.actionActorBiographyFragmentToDetailsFragment(
                    movieId))
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setCollectors() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actorInfo.collect { actor ->
                    collapsingToolbar.title = actor?.name
                    val imageUrl = "https://image.tmdb.org/t/p/w500${actor?.profile_path}"
                    if (!actor?.profile_path.isNullOrEmpty()) {
                        Glide.with(binding.root)
                            .load(imageUrl)
                            .placeholder(R.color.gray)
                            .into(actorImage)
                    } else {
                        actorImage.setImageResource(R.color.red)
                    }

                    actorName.text = actor?.name
                    biography.text = actor?.biography
                    born.text = actor?.birthday
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actorFilmography.collect { actorFilmography ->
                    actorFilmographyAdapter.submitList(actorFilmography?.cast)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMessage.collect { error ->
                    Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}