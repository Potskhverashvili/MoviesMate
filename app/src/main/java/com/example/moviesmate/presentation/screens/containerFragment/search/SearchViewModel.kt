package com.example.moviesmate.presentation.screens.containerFragment.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.data.pagingSourse.MoviesPagingSource
import com.example.moviesmate.data.pagingSourse.MoviesPagingSourceByGenre
import com.example.moviesmate.data.repository.MoviesRepositoryImpl
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.repository.MoviesRepository
import com.example.moviesmate.domain.usecases.FetchGenresTypesUseCase
import com.example.moviesmate.domain.usecases.FetchMoviesByGenreUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchGenresTypesUseCase: FetchGenresTypesUseCase,
    private val fetchMoviesByGenreUseCase: FetchMoviesByGenreUseCase,
    private val moviePagingSource: MoviesPagingSource
) : ViewModel() {

    init {
        fetchAllGenres()
    }

    private val _genresFlow = MutableStateFlow<List<GenresType.Genre>>(emptyList())
    val genresFlow: StateFlow<List<GenresType.Genre>> = _genresFlow

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    val categoryMoviesFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 18
        ),
        pagingSourceFactory = { moviePagingSource }
    ).flow.cachedIn(viewModelScope)

    fun getGenreMovies(id: Int) = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 18
        ),
        pagingSourceFactory = { MoviesPagingSourceByGenre(fetchMoviesByGenreUseCase, id) }
    ).flow.cachedIn(viewModelScope)

    private fun fetchAllGenres() = viewModelScope.launch {
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


