package com.example.moviesmate.presentation.screens.containerFragment.favorites

import android.util.Log.d
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesmate.data.toMovieDbo
import com.example.moviesmate.databinding.FragmentFavoritesBinding
import com.example.moviesmate.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel by viewModel<FavoritesViewModel>()
    private val favoritesAdapter = FavoritesAdapter()

    override fun viewCreated() {
        prepareRecyclerViews()
        setListeners()
        setCollectors()
        showAllFavorite()
    }

    private fun prepareRecyclerViews() {
        binding.favoritesRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun showAllFavorite() {
        viewModel.showAllSavedMovies()
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allSavedMovies.collect { allMovies ->
                    d("All movies", "Movies : $allMovies")
                    favoritesAdapter.submitList(allMovies)
                }
            }
        }
    }

    private fun setListeners() {
        favoritesAdapter.onItemDeleteClick = { movie ->
            viewModel.deleteSavedMovie(movie)
        }
    }
}