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

class SearchViewModel(
    private val fetchGenresTypesUseCase: FetchGenresTypesUseCase,
    private val searchedCategoryUseCase: SearchedCategoryUseCase
) : ViewModel() {

    private var _categoryMoviesFlow =
        MutableStateFlow<List<CategoryMovies.Result>>(emptyList())
    val categoryMoviesFlow: StateFlow<List<CategoryMovies.Result>> = _categoryMoviesFlow

    private var _genresFlow = MutableStateFlow<List<GenresType.Genre>>(emptyList())
    val genresFlow: StateFlow<List<GenresType.Genre>> = _genresFlow

    private var _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private var _isLoadingState = MutableSharedFlow<Boolean>()
    val isLoadingState: SharedFlow<Boolean> = _isLoadingState

    init {
        getAllGenresType()
    }

    fun getCategoryMovies() = viewModelScope.launch {
        when (val status = searchedCategoryUseCase.execute()) {
            is OperationStatus.Success -> {
                Log.d("SearchFragment", "Category result = ${status.value.results}")
                _categoryMoviesFlow.emit(status.value.results)
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.message)
            }
        }
    }


    private fun getAllGenresType() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = fetchGenresTypesUseCase.execute()) {
            is OperationStatus.Success -> {
                _genresFlow.emit(status.value.genres)
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.toString())
            }
        }
        _isLoadingState.emit(false)
    }
}