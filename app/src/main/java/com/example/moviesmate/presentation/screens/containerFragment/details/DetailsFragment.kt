package com.example.moviesmate.presentation.screens.containerFragment.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.FragmentDetailsBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailsViewModel>()
    private val actorDetailAdapter = ActorDetailAdapter()

    override fun viewCreated() {
        prepareRecyclerViewCast()
        getMovieDetails()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerViewCast() {
        binding.recyclerviewCast.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorDetailAdapter
        }
    }

    private fun setListeners() {
        actorDetailAdapter.onItemClick = { id ->
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToActorBiographyFragment(actorId = id)
            )
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(args.movieId)
        viewModel.getActorDetails(args.movieId)
    }

    @SuppressLint("DefaultLocale")
    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collect {
                    binding.titleTv.text = it?.original_title
                    binding.imdbRating.text =
                        it?.vote_average?.let { rating -> String.format("%.1f", rating) }
                    binding.genreTv.text =
                        it?.genres?.joinToString(", ") { it.name.toString() } ?: ""
                    binding.lengthTv.text = it?.runtime?.let { runtime -> "$runtime min" }
                    binding.descriptionTv.text = it?.overview.toString()
                    val imageUrl = "https://image.tmdb.org/t/p/w500${it?.poster_path}"
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(binding.imgMealDetails)
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actorDetails.collect { actors ->
                    actorDetailAdapter.submitList(actors?.cast)
                }
            }
        }
    }

}