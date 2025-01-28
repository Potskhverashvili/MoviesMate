package com.example.moviesmate.presentation.screens.containerFragment.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.data.local.entity.MovieDbo
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.usecases.DeleteFromFavoritesUsaCase
import com.example.moviesmate.domain.usecases.GetAllFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteFromFavoritesUsaCase : DeleteFromFavoritesUsaCase
) : ViewModel() {

    private val _allSavedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val allSavedMovies: StateFlow<List<Movie>> = _allSavedMovies

    fun showAllSavedMovies() = viewModelScope.launch {
        when (val status = getAllFavoritesUseCase.execute()) {
            is OperationStatus.Success -> {
                _allSavedMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun deleteSavedMovie(movie: MovieDbo) = viewModelScope.launch {
        when (deleteFromFavoritesUsaCase.execute(movie)) {
            is OperationStatus.Success -> {
                showAllSavedMovies()
            }

            is OperationStatus.Failure -> {}
        }
    }


}