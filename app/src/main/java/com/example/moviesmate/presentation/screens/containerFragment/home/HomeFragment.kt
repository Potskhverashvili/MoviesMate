package com.example.moviesmate.presentation.screens.containerFragment.home

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.FragmentHomeBinding
import com.example.moviesmate.core.base.BaseFragment
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by viewModel<HomeViewModel>()
    private val upcomingMoviesAdapter = UpcomingMoviesAdapter()
    private val popularMoviesAdapter = PopularMoviesAdapter()

    override fun viewCreated() {
        getUserName()
        getGreeting()
        getProfileImage()
        prepareRecyclerview()
        setListeners()
        setCollectors()
    }

    private fun getGreeting() {
        viewModel.getGreeting()
    }

    private fun getProfileImage() {
        viewModel.fetchUserProfileImage()
    }

    private fun getUserName() {
        viewModel.getUsername()
    }

    private fun prepareCarouselRecyclerView() {
        binding.recyclerViewUpcomingMovies.setHasFixedSize(true)
        binding.recyclerViewUpcomingMovies.layoutManager = CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(binding.recyclerViewUpcomingMovies)
        binding.recyclerViewUpcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun prepareRecyclerview() {
        prepareCarouselRecyclerView()
        binding.recyclerViewPopularMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMoviesAdapter
        }
    }

    private fun setListeners() {
        upcomingMoviesAdapter.onItemClick = { movie ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movie.id ?: -1
                )
            )
        }

        popularMoviesAdapter.onItemClick = { movie ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movie.id ?: -1
                )
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homePageMovies.collect { upcomingMovies ->
                    upcomingMoviesAdapter.submitList(upcomingMovies?.results)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularMovies.collect { popularMovies ->
                    val results = popularMovies?.results ?: emptyList()
                    popularMoviesAdapter.submitList(results)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedImageUri.collect { uri ->
                    uri?.let {
                        Glide.with(this@HomeFragment)
                            .load(it)
                            .into(binding.userImage)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.username.collect { username ->
                    binding.userName.text = username
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.greeting.collect { greetingText ->
                    binding.textviewHey.text = greetingText
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingState.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    binding.upcomingMoviesTv.visibility =
                        if (isLoading) View.INVISIBLE else View.VISIBLE
                    binding.popularMoviesTv.visibility =
                        if (isLoading) View.INVISIBLE else View.VISIBLE
                }
            }
        }

    }
}