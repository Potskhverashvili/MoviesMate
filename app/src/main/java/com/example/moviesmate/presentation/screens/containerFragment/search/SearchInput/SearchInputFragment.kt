package com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.searchMovieWithQuery.collect { movie ->
                searchInputAdapter.submitList(movie?.results)
            }
        }
    }

}