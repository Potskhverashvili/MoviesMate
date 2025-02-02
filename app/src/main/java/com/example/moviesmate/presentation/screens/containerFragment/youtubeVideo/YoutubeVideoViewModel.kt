package com.example.moviesmate.presentation.screens.containerFragment.youtubeVideo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.Youtube
import com.example.moviesmate.domain.usecases.YoutubeVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class YoutubeVideoViewModel(
    private val youtubeVideoUseCase: YoutubeVideoUseCase
) : ViewModel() {

    private val _movieTrailer = MutableStateFlow<Youtube?>(null)
    val movieTrailer = _movieTrailer.asStateFlow()

    fun fetchMovieTrailer(movieId: String) = viewModelScope.launch {
        when (val status = youtubeVideoUseCase.execute(id = movieId)) {
            is OperationStatus.Success -> {
                _movieTrailer.emit(status.value)
            }

            is OperationStatus.Failure -> {

            }
        }
    }

}