package com.example.moviesmate.presentation.screens.containerFragment.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.usecases.FetchGenresTypesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchGenresTypesUseCase: FetchGenresTypesUseCase
) : ViewModel() {

    private val _genresFlow = MutableStateFlow<List<GenresType.Genre>>(emptyList())
    val genresFlow: StateFlow<List<GenresType.Genre>> = _genresFlow

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private val _isLoadingState = MutableSharedFlow<Boolean>()
    val isLoadingState: SharedFlow<Boolean> = _isLoadingState

    init {
        getAllGenresType()
    }

    private fun getAllGenresType() = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = fetchGenresTypesUseCase.execute()) {
            is OperationStatus.Success -> {
                _genresFlow.emit(genresFlow.value)
            }

            is OperationStatus.Failure -> {
                Log.d("SearchFragment", "Error === ${status.exception}")
                _showError.emit(status.exception.toString())
            }
        }
        _isLoadingState.emit(false)
    }

}