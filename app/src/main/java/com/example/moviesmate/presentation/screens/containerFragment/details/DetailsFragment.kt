package com.example.moviesmate.presentation.screens.containerFragment.details

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesmate.R
import com.example.moviesmate.data.toMovie
import com.example.moviesmate.databinding.FragmentDetailsBinding
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailsViewModel>()
    private val actorDetailAdapter = ActorDetailAdapter()
    private lateinit var movie: MovieDetails
    private var isFavorite: Boolean = false

    override fun viewCreated() {
        checkIfMovieIsSaved()
        prepareRecyclerViewCast()
        getMovieDetails()
        setListeners()
        setCollectors()
    }

    private fun checkIfMovieIsSaved() {
        viewModel.checkIfMovieIsSaveInRoom(args.movieId)
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

        binding.btnPlayVideo.setOnClickListener {
            goToYoutubeVideo()
        }

        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                movie.toMovie()?.let { it1 -> viewModel.deleteSavedMovie(it1) }
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_state) // Set to unfilled favorite icon
            } else {
                viewModel.saveToFavorite(movie)
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite) // Set to filled favorite icon
            }
            isFavorite = !isFavorite // Toggle favorite state
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(args.movieId)
        viewModel.getActorDetails(args.movieId)
    }

    private fun goToYoutubeVideo() {
        findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToYoutubeVideoFragment(args.movieId))
    }

    @SuppressLint("DefaultLocale")
    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collect {
                    if (it != null) {
                        movie = it
                    }
                    val releaseDateString = it?.release_date
                    val year = releaseDateString?.takeIf { it.length >= 4 }?.substring(0, 4)
                    binding.releaseDate.text = year ?: "Unknown"
                    Log.d("MyLog", "${releaseDateString}")

                    binding.collapsingToolbar.title = it?.title
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavoriteMovie.collect { isFav ->
                    isFavorite = isFav
                    binding.btnFavorite.setImageResource(
                        if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_state
                    )
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        checkIfMovieIsSaved()
    }

}