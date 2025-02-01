package com.example.moviesmate.presentation.screens.containerFragment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.data.toMovie
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.usecases.ActorDetailsUseCase
import com.example.moviesmate.domain.usecases.MovieDetailsUseCase
import com.example.moviesmate.domain.usecases.SaveToFavoriteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val actorDetailsUseCase: ActorDetailsUseCase,
    private val saveToFavoriteUseCase: SaveToFavoriteUseCase
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    private val _actorDetails = MutableStateFlow<ActorDetails?>(null)
    val actorDetails = _actorDetails.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        when (val status = movieDetailsUseCase.execute(movieId = movieId)) {
            is OperationStatus.Success -> {
                _movieDetails.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit(status.exception.toString())
            }
        }
    }

    fun getActorDetails(movieId: Int) = viewModelScope.launch {
        when (val status = actorDetailsUseCase.execute(movieId = movieId)) {
            is OperationStatus.Success -> {
                _actorDetails.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit(status.exception.toString())
            }
        }
    }

    fun saveToFavorite(movie: MovieDetails) = viewModelScope.launch {
        movie.toMovie()?.let { saveToFavoriteUseCase.execute(it) }
    }
}