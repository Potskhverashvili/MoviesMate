package com.example.moviesmate.presentation.screens.containerFragment.favorites

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesmate.databinding.FragmentFavoritesBinding
import com.example.moviesmate.core.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel by viewModel<FavoritesViewModel>()
    private val favoritesAdapter = FavoritesAdapter()

    override fun viewCreated() {
        showAllSavedMovies()
        prepareRecyclerViews()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerViews() {
        binding.favoritesRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun showAllSavedMovies() {
        viewModel.showAllSavedMovies()
    }

    private fun setListeners() {
        favoritesAdapter.onItemDeleteClick = { movie ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Movie")
                .setMessage("Are you sure you want to delete this movie from your favorites?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteSavedMovie(movie)
                }
                .setNegativeButton("No", null)
                .show()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        favoritesAdapter.onItemClick = { movie ->
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                    movie.id ?: -1
                )
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allSavedMovies.collect { allMovies ->
                    favoritesAdapter.submitList(allMovies)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noMoviesMessageVisible.collect { isVisible ->
                    if (isVisible) {
                        showNoMoviesMessage()
                    } else {
                        showSavedMovies()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showError.collect { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun showNoMoviesMessage() = with(binding) {
        noMoviesAnimation.visibility = View.VISIBLE
        noMoviesMessage.visibility = View.VISIBLE
    }

    private fun showSavedMovies() = with(binding) {
        noMoviesAnimation.visibility = View.GONE
        noMoviesMessage.visibility = View.GONE
    }
}