package com.example.moviesmate.presentation.screens.containerFragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.data.pagingSourse.MoviesPagingSource
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.usecases.FetchGenresTypesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchGenresTypesUseCase: FetchGenresTypesUseCase,
    private val moviePagingSource: MoviesPagingSource
) : ViewModel() {

    val categoryMoviesFlow = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { moviePagingSource }
    ).flow.cachedIn(viewModelScope)

    private var _genresFlow = MutableStateFlow<List<GenresType.Genre>>(emptyList())
    val genresFlow: StateFlow<List<GenresType.Genre>> = _genresFlow

    private var _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private var _isLoadingState = MutableStateFlow<Boolean>(false)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    init {
        getAllGenresType()
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
