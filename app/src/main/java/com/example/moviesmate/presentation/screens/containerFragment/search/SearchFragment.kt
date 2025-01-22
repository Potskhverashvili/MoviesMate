package com.example.moviesmate.presentation.screens.containerFragment.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesmate.databinding.FragmentSearchBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val searchViewModel by viewModel<SearchViewModel>()
    private val categoryAdapter = CategoryAdapter()
    private val searchAdapter = SearchAdapter()

    override fun viewCreated() {
        prepareRecyclerViews()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerViews() {
        binding.categoriesRecyclerView.apply {
            adapter = categoryAdapter
        }
        binding.searchRecyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }

    }

    private fun setListeners() {
        categoryAdapter.onItemClick = { selectedGenre ->
            searchViewModel.filterMoviesByGenre(selectedGenre.id)
            categoryAdapter.setSelectedCategory(selectedGenre.id)
        }

        binding.btnSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val query = charSequence.toString().trim()
                searchViewModel.searchMovies(query)
            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })
    }

    private fun setCollectors() {
        // Collect filtered movies to update the search adapter
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.filteredMoviesFlow.collect { categoryMovies ->
                searchAdapter.submitList(categoryMovies)
            }
        }

        // Collect genres to update the category adapter
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.genresFlow.collect { genres ->
                categoryAdapter.submitList(genres)
            }
        }

        // Get Initial All Movies Page1
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.categoryMoviesFlow.collect { allMovies ->
                searchAdapter.submitList(allMovies)
            }
        }

        // Handle errors if needed
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.isLoadingState.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE // Show progress bar
                } else {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                }
            }
        }
    }
}