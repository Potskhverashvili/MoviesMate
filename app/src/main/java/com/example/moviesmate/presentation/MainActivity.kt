package com.example.moviesmate.presentation

import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.moviesmate.R
import com.example.moviesmate.core.NetworkChangeReceiver
import com.example.moviesmate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val user = FirebaseAuth.getInstance()
    private lateinit var networkReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (user.currentUser != null) {
            navGraph.setStartDestination(R.id.containerFragment)
        } else {
            navGraph.setStartDestination(R.id.registerFragment)
        }
        navController.graph = navGraph

        networkReceiver = NetworkChangeReceiver { isConnected ->
            handleNetworkChange(isConnected)
        }
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun handleNetworkChange(isConnected: Boolean) {
        if (isConnected) {
            binding.fragmentContainerView.visibility = View.VISIBLE
            binding.internetError.visibility = View.GONE
        } else {
            binding.fragmentContainerView.visibility = View.GONE
            binding.internetError.visibility = View.VISIBLE
            binding.internetError.setAnimation(R.raw.error)
            binding.internetError.playAnimation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }
    

}
