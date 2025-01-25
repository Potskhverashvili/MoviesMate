package com.example.moviesmate.presentation.screens.containerFragment.search

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesmate.databinding.FragmentSearchBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val searchViewModel by viewModel<SearchViewModel>()
    private val categoryAdapter = CategoryAdapter()
    private val searchAdapter = SearchAdapter()
    private val searchedSpecificGenreAdapter = SearchedSpecificGenreAdapter()
    private val loadStateAdapter = MovieLoadStateAdapter()

    override fun viewCreated() {
        prepareRecyclerViews()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerViews() {
        binding.categoriesRecyclerView.adapter = categoryAdapter

        binding.searchRecyclerView.apply {
            adapter = searchAdapter.withLoadStateFooter(loadStateAdapter)
        }

        binding.chooseGenreRecyclerview.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2, GridLayoutManager.VERTICAL, false
            )
            adapter = searchedSpecificGenreAdapter.withLoadStateFooter(loadStateAdapter)
        }

    }

    private fun setListeners() {
        categoryAdapter.onGenreClick = { selectedGenre ->
            categoryAdapter.updateSelectedGenre(selectedGenre.id)
            binding.searchRecyclerView.visibility = View.GONE
            binding.chooseGenreRecyclerview.visibility = View.VISIBLE
            searchViewModel.getGenreMovies(selectedGenre.id)

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    searchViewModel.getGenreMovies(selectedGenre.id).collectLatest { pagingData ->
                        searchedSpecificGenreAdapter.submitData(pagingData)
                    }
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            goToSearchInputFragment()
        }
    }

    private fun goToSearchInputFragment() {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToSearchInputFragment())
    }

    private fun setCollectors() {
        // Collect genres to update the category adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.genresFlow.collect { genres ->
                    categoryAdapter.submitList(genres)
                }
            }
        }

        // Collect all movies for the initial search
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.categoryMoviesFlow.collect { allMovies ->
                    searchAdapter.submitData(allMovies)
                }
            }
        }

        // Show Error
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.showError.collect { error ->
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Collect and handle loading state
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.isLoadingState.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }
}
