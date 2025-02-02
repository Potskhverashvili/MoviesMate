package com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesmate.databinding.FragmentSearchInputBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchInputFragment :
    BaseFragment<FragmentSearchInputBinding>(FragmentSearchInputBinding::inflate) {
    private val searchInputAdapter = SearchInputAdapter()
    private val viewmodel by viewModel<SearchInputViewModel>()
    private val shimmerLoader by lazy { binding.shimmerLayout }

    override fun viewCreated() {
        prepareRecyclerView()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerView() {
        binding.recyclerviewSearch.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = searchInputAdapter
        }
    }

    private fun setListeners() {
        binding.btnSearch.doAfterTextChanged { newInputQuery ->
            viewmodel.searchedMovieWithQuery(newInputQuery.toString())
        }

        searchInputAdapter.onItemClick = { currentMovie ->
            findNavController().navigate(
                SearchInputFragmentDirections.actionSearchInputFragmentToDetailsFragment(
                    currentMovie.id))
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.searchMovieWithQuery.collect { movie ->
                searchInputAdapter.submitList(movie?.results)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.isLoading.collect { isLoading ->
                if (isLoading) {
                    shimmerLoader.startShimmer()
                    shimmerLoader.visibility = View.VISIBLE
                    binding.recyclerviewSearch.visibility = View.GONE
                } else {
                    shimmerLoader.stopShimmer()
                    shimmerLoader.visibility = View.GONE
                    binding.recyclerviewSearch.visibility = View.VISIBLE
                }
            }
        }
    }

}