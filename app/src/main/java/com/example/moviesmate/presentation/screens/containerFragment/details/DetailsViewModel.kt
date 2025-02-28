package com.example.moviesmate.presentation.screens.containerFragment.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.usecases.ActorDetailsUseCase
import com.example.moviesmate.domain.usecases.DeleteFromFavoritesUsaCase
import com.example.moviesmate.domain.usecases.GetAllFavoritesUseCase
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
    private val saveToFavoriteUseCase: SaveToFavoriteUseCase,
    private val deleteFromFavoritesUsaCase: DeleteFromFavoritesUsaCase,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    private val _actorDetails = MutableStateFlow<ActorDetails?>(null)
    val actorDetails = _actorDetails.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _isFavoriteMovie = MutableStateFlow(false)
    val isFavoriteMovie = _isFavoriteMovie.asStateFlow()

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

    fun saveToFavorite(movieDetail: MovieDetails) = viewModelScope.launch {
//        movieDetail.toMovie()?.let { saveToFavoriteUseCase.execute(it) }
//        _isFavoriteMovie.emit(true)
        when (saveToFavoriteUseCase.execute(movieDetail)) {
            is OperationStatus.Success -> {
                Log.d("MyLog", "success id == ${movieDetail.id}")
                _isFavoriteMovie.emit(true)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit("Failed To Save Movie")
            }
        }
        Log.d("MyLog", "${movieDetail.id}")
    }


    fun deleteSavedMovie(movie: Movie) = viewModelScope.launch {
        when (deleteFromFavoritesUsaCase.execute(movie)) {
            is OperationStatus.Success -> {
                _isFavoriteMovie.emit(false)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun checkIfMovieIsSaveInRoom(id: Int) = viewModelScope.launch {
        when (val status = getAllFavoritesUseCase.execute()) {
            is OperationStatus.Success -> {
                val favorites = status.value
                _isFavoriteMovie.emit(favorites.any { it.id == id })
            }

            is OperationStatus.Failure -> {}
        }
    }
}