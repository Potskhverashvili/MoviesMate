package com.example.moviesmate.presentation.screens.containerFragment.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.HomePageMovies
import com.example.moviesmate.domain.usecases.GetUserNameUseCase
import com.example.moviesmate.domain.usecases.GetUserProfileImageUseCase
import com.example.moviesmate.domain.usecases.PopularMoviesUseCase
import com.example.moviesmate.domain.usecases.UpcomingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val upcomingMoviesUseCase: UpcomingMoviesUseCase,
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserProfileImageUseCase: GetUserProfileImageUseCase
) : ViewModel() {

    private val _homePageMovies = MutableStateFlow<HomePageMovies?>(null)
    val homePageMovies: StateFlow<HomePageMovies?> = _homePageMovies

    private val _popularMovies = MutableStateFlow<HomePageMovies?>(null)
    val popularMovies: StateFlow<HomePageMovies?> = _popularMovies

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    init {
        fetchUpcomingMovies()
        fetchPopularMovies()
    }

    fun fetchUpcomingMovies() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = upcomingMoviesUseCase.execute()) {
            is OperationStatus.Success -> {
                _homePageMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
        _isLoadingState.emit(false)
    }

    fun fetchPopularMovies() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = popularMoviesUseCase.execute()) {
            is OperationStatus.Success -> {
                _popularMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
        _isLoadingState.emit(false)
    }

    fun getUsername() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val result = getUserNameUseCase.execute()) {
            is OperationStatus.Success -> {
                _username.emit(result.value.toString())
            }

            is OperationStatus.Failure -> {

            }
        }
        _isLoadingState.emit(false)
    }

    fun fetchUserProfileImage() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = getUserProfileImageUseCase.execute()) { // Fetch URL from Firebase
            is OperationStatus.Success -> {
                _selectedImageUri.emit(Uri.parse(status.value)) // Emit image URL
            }

            is OperationStatus.Failure -> {
                _selectedImageUri.emit(null) // Set null if image doesn't exist

            }
        }

        _isLoadingState.emit(false)
    }

}