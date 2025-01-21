package com.example.moviesmate.presentation.screens.containerFragment.search

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.moviesmate.databinding.FragmentSearchBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val searchViewModel by viewModel<SearchViewModel>()

    override fun viewCreated() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.genresFlow.collect {
                Log.d("SearchFragment", "result === ${it}")
            }
        }
    }

}