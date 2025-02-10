package com.example.moviesmate.presentation.screens.containerFragment

import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviesmate.R
import com.example.moviesmate.databinding.FragmentContainerBinding
import com.example.moviesmate.core.base.BaseFragment

class ContainerFragment :
    BaseFragment<FragmentContainerBinding>(FragmentContainerBinding::inflate) {
    private lateinit var navController: NavController

    override fun viewCreated() {
        setUp()
    }

    private fun setUp() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerViewTwo) as? NavHostFragment

        if (navHostFragment == null) {
            Log.e("HomePageFragment", "NavHostFragment is null")
            return
        }
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        // Add listener for destination changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailsFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }

                R.id.actorBiographyFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }

                R.id.searchInputFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }

    }
}