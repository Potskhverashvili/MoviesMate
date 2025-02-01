package com.example.moviesmate.presentation.screens.containerFragment.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesmate.databinding.FragmentHomeBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by viewModel<HomeViewModel>()
    private val upcomingMoviesAdapter = UpcomingMoviesAdapter()
    private val popularMoviesAdapter = PopularMoviesAdapter()

    override fun viewCreated() {
        getUserName()
        prepareRecyclerview()
        setListeners()
        setCollectors()
    }

    private fun getUserName() {
        viewModel.getUsername()
    }

    private fun prepareRecyclerview() {
        binding.recyclerViewUpcomingMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMoviesAdapter
        }

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
            viewModel.homePageMovies.collect { upcomingMovies ->
                upcomingMoviesAdapter.submitList(upcomingMovies?.results)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collect { popularMovies ->
                val results = popularMovies?.results ?: emptyList() // Default to empty list if null
                popularMoviesAdapter.submitList(results)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.username.collect { username ->
                binding.userName.text = username
            }
        }
    }
}