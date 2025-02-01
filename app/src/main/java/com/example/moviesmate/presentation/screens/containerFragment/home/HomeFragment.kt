package com.example.moviesmate.presentation.screens.containerFragment.home

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesmate.databinding.FragmentHomeBinding
import com.example.moviesmate.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val viewModel by viewModel<HomeViewModel>()
    private val upcomingMoviesAdapter = UpcomingMoviesAdapter()
    private val popularMoviesAdapter = PopularMoviesAdapter()

    override fun viewCreated() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        binding.userName.text = username
                    }
                }
        } else {
            Toast.makeText(requireContext(), "UserName", Toast.LENGTH_SHORT).show()
        }

        prepareRecyclerview()
        setListeners()
        setCollectors()
    }

    private fun prepareRecyclerview() {
        binding.recyclerViewUpcomingMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMoviesAdapter
        }

        binding.recyclerViewPopularMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMoviesAdapter
        }
    }

    private fun setListeners() {
        upcomingMoviesAdapter.onItemClick = {movie->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie.id ?: -1))
        }

        popularMoviesAdapter.onItemClick = {movie->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie.id ?: -1))
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homePageMovies.collect { upcomingMovies ->
                upcomingMoviesAdapter.submitList(upcomingMovies?.results)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collect { popularMovies ->
                val results = popularMovies?.results ?: emptyList() // Default to empty list if null
                popularMoviesAdapter.submitList(results)
            }
        }
    }


}