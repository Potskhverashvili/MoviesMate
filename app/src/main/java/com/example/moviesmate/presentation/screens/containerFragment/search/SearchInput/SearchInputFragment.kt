package com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesmate.databinding.FragmentSearchInputBinding
import com.example.moviesmate.presentation.base.BaseFragment
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
                    currentMovie.id
                )
            )
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
                    Log.d("MyFragment", "isLoading $isLoading")
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



    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("MyFragment", "----------------\nonAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyFragment", "onCreate() called")
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyFragment", "onViewCreated() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyFragment", "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyFragment", "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyFragment", "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyFragment", "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("MyFragment", "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyFragment", "onDestroy() called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("MyFragment", "onDetach() called")
    }

}