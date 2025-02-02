package com.example.moviesmate.presentation.screens.containerFragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.HomePageMovies
import com.example.moviesmate.domain.usecases.GetUserNameUseCase
import com.example.moviesmate.domain.usecases.PopularMoviesUseCase
import com.example.moviesmate.domain.usecases.UpcomingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val upcomingMoviesUseCase: UpcomingMoviesUseCase,
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val getUserNameUseCase: GetUserNameUseCase
) : ViewModel() {

    private val _homePageMovies = MutableStateFlow<HomePageMovies?>(null)
    val homePageMovies: StateFlow<HomePageMovies?> = _homePageMovies

    private val _popularMovies = MutableStateFlow<HomePageMovies?>(null)
    val popularMovies: StateFlow<HomePageMovies?> = _popularMovies

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    init {
        fetchUpcomingMovies()
        fetchPopularMovies()
    }

    fun fetchUpcomingMovies() = viewModelScope.launch {
        when (val status = upcomingMoviesUseCase.execute()) {
            is OperationStatus.Success -> {
                _homePageMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun fetchPopularMovies() = viewModelScope.launch {
        when (val status = popularMoviesUseCase.execute()) {
            is OperationStatus.Success -> {
                _popularMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun getUsername() = viewModelScope.launch {
        when (val result = getUserNameUseCase.execute()) {
            is OperationStatus.Success -> {
                _username.emit(result.value.toString())
            }

            is OperationStatus.Failure -> {

            }
        }
    }

}