package com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.SearchInput
import com.example.moviesmate.domain.usecases.SearchMovieInputUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchInputViewModel(
    private val searchMovieInputUseCase: SearchMovieInputUseCase,
) : ViewModel() {
    private var searchJob: Job? = null

    private var _searchMovieWithQuery = MutableStateFlow<SearchInput?>(null)
    val searchMovieWithQuery = _searchMovieWithQuery.asStateFlow()

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    fun searchedMovieWithQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.emit(true)
            delay(600)
            try {
                when (val status = searchMovieInputUseCase.execute(query)) {
                    is OperationStatus.Success -> {
                        _searchMovieWithQuery.emit(status.value)
                    }

                    is OperationStatus.Failure -> {}
                }
            } finally {
                _isLoading.emit(false)
            }
        }
    }

}