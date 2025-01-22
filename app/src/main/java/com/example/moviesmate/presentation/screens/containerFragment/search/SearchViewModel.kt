package com.example.moviesmate.presentation.screens.containerFragment.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.usecases.FetchGenresTypesUseCase
import com.example.moviesmate.domain.usecases.SearchedCategoryUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State

class SearchViewModel(
    private val fetchGenresTypesUseCase: FetchGenresTypesUseCase,
    private val searchedCategoryUseCase: SearchedCategoryUseCase
) : ViewModel() {

    private var _categoryMoviesFlow =
        MutableStateFlow<List<CategoryMovies.Result>>(emptyList())
    val categoryMoviesFlow: StateFlow<List<CategoryMovies.Result>> = _categoryMoviesFlow

    private var _filteredMoviesFlow =
        MutableStateFlow<List<CategoryMovies.Result>>(emptyList())
    val filteredMoviesFlow: StateFlow<List<CategoryMovies.Result>> = _filteredMoviesFlow


    private var _genresFlow = MutableStateFlow<List<GenresType.Genre>>(emptyList())
    val genresFlow: StateFlow<List<GenresType.Genre>> = _genresFlow

    private var _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private var _isLoadingState = MutableStateFlow<Boolean>(false)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    init {
        getAllGenresType()
        getCategoryMovies()
    }

    private fun getCategoryMovies() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = searchedCategoryUseCase.execute()) {
            is OperationStatus.Success -> {
                Log.d("SearchFragment", "Category result = ${status.value.results}")
                _categoryMoviesFlow.emit(status.value.results)
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.message)
            }
        }
        _isLoadingState.emit(false)
    }

    private fun getAllGenresType() = viewModelScope.launch {
        when (val status = fetchGenresTypesUseCase.execute()) {
            is OperationStatus.Success -> {
                _genresFlow.emit(status.value.genres)
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.toString())
            }
        }
    }


    fun filterMoviesByGenre(genreId: Int) = viewModelScope.launch {
        val filteredMovies = _categoryMoviesFlow.value.filter { movie ->
            movie.genre_ids.contains(genreId)
        }
        _filteredMoviesFlow.emit(filteredMovies)
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        val filteredMovies = _categoryMoviesFlow.value.filter { movie ->
            movie.title.contains(query, ignoreCase = true)
        }
        _filteredMoviesFlow.emit(filteredMovies)
    }


}