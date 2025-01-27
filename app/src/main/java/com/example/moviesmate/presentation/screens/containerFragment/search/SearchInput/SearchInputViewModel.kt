package com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.SearchInput
import com.example.moviesmate.domain.usecases.SearchMovieInputUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchInputViewModel(
    private val searchMovieInputUseCase: SearchMovieInputUseCase
) : ViewModel() {
    private var searchJob: Job? = null

    private var _searchMovieWithQuery = MutableStateFlow<SearchInput?>(null)
    val searchMovieWithQuery = _searchMovieWithQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun searchedMovieWithQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.emit(true)
            delay(600)
            when(val status = searchMovieInputUseCase.execute(query)){
                is OperationStatus.Success -> {
                    _searchMovieWithQuery.emit(status.value)
                }
                is OperationStatus.Failure -> {

                }
            }
            _isLoading.emit(false)
        }
    }

}