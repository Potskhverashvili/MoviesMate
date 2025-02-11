package com.example.moviesmate.presentation.screens.containerFragment.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.usecases.DeleteFromFavoritesUsaCase
import com.example.moviesmate.domain.usecases.GetAllFavoritesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteFromFavoritesUsaCase: DeleteFromFavoritesUsaCase
) : ViewModel() {

    private val _allSavedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val allSavedMovies: StateFlow<List<Movie>> = _allSavedMovies

    private val _noMoviesMessageVisible = MutableStateFlow(false)
    val noMoviesMessageVisible: StateFlow<Boolean> = _noMoviesMessageVisible

    private val _showError = MutableSharedFlow<String>()
    val showError = _showError.asSharedFlow()

    fun showAllSavedMovies() = viewModelScope.launch {
        when (val status = getAllFavoritesUseCase.execute()) {
            is OperationStatus.Success -> {
                _allSavedMovies.emit(status.value)
                _noMoviesMessageVisible.emit(status.value.isEmpty())
            }

            is OperationStatus.Failure -> {
                _noMoviesMessageVisible.emit(true)
                _showError.emit(status.exception.message.toString())
            }
        }
    }

    fun deleteSavedMovie(movie: Movie) = viewModelScope.launch {
        when (val status = deleteFromFavoritesUsaCase.execute(movie)) {
            is OperationStatus.Success -> {
                showAllSavedMovies()
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.message.toString())
            }
        }
    }
}