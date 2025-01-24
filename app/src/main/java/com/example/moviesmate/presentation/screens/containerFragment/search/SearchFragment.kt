package com.example.moviesmate.presentation.screens.containerFragment.search

import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.searchRecyclerView.apply { adapter = searchAdapter.withLoadStateFooter(loadStateAdapter) }
        binding.chooseGenreRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(),
                2, GridLayoutManager.VERTICAL, false)
            adapter = searchedSpecificGenreAdapter.withLoadStateFooter(loadStateAdapter) }
    }

    private fun setListeners() {
        categoryAdapter.onGenreClick = { selectedGenre ->
            categoryAdapter.updateSelectedGenre(selectedGenre.id)
            binding.searchRecyclerView.visibility = View.GONE
            binding.chooseGenreRecyclerview.visibility = View.VISIBLE
            searchViewModel.fetchMoviesByGenre(selectedGenre.id)
        }
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

        // Collect paginated genre-specific movies
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.genreMoviesFlow.collectLatest { pagingData ->
                    Log.d("GenreMoviesFlow", "New genre movies received: $pagingData")
                    searchedSpecificGenreAdapter.submitData(pagingData)
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
