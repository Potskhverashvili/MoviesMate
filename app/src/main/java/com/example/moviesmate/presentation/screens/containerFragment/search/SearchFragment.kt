package com.example.moviesmate.presentation.screens.containerFragment.search

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
            categoryAdapter.setSelectedCategory(selectedGenre.id)
        }
    }

    private fun setCollectors() {
        // Collect genres to update the category adapter
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.genresFlow.collect { genres ->
                categoryAdapter.submitList(genres)
            }
        }

        // Get Initial All Movies Pages1,2,3...
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.categoryMoviesFlow.collect { allMovies ->
                searchAdapter.submitData(allMovies)
            }
        }

        // Handle errors if needed
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.isLoadingState.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}