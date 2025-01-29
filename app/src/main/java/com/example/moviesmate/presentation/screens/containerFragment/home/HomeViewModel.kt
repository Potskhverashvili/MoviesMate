package com.example.moviesmate.presentation.screens.containerFragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.UpcomingMovies
import com.example.moviesmate.domain.usecases.UpcomingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val upcomingMoviesUseCase: UpcomingMoviesUseCase
) : ViewModel() {

    private val _upcomingMovies = MutableStateFlow<UpcomingMovies?>(null)
    val upcomingMovies: StateFlow<UpcomingMovies?> = _upcomingMovies

    init {
        fetchUpcomingMovies()
    }

    fun fetchUpcomingMovies() = viewModelScope.launch {
        when (val status = upcomingMoviesUseCase.execute()) {
            is OperationStatus.Success -> {
                _upcomingMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

}