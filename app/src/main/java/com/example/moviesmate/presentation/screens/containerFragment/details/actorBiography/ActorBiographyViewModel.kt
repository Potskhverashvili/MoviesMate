package com.example.moviesmate.presentation.screens.containerFragment.details.actorBiography

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.AboutActor
import com.example.moviesmate.domain.model.ActorFilmography
import com.example.moviesmate.domain.usecases.AboutActorDetailsInfoUseCase
import com.example.moviesmate.domain.usecases.ActorFilmographyUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorBiographyViewModel(
    private val aboutActorDetailsInfoUseCase: AboutActorDetailsInfoUseCase,
    private val actorFilmographyUseCase: ActorFilmographyUseCase
) : ViewModel() {

    private val _actorInfo = MutableStateFlow<AboutActor?>(null)
    val actorInfo = _actorInfo.asStateFlow()

    private val _actorFilmography = MutableStateFlow<ActorFilmography?>(null)
    val actorFilmography = _actorFilmography.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun getInfoAboutActor(actorId: Int) = viewModelScope.launch {
        when (val status = aboutActorDetailsInfoUseCase.execute(actorId = actorId)) {
            is OperationStatus.Success -> {
                Log.d("ActorBiographyViewModel", "Filmography fetched: ${status.value}")
                _actorInfo.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit(status.exception.toString())
            }
        }
    }

    fun getActorFilmography(actorId: Int) = viewModelScope.launch {
        when (val status = actorFilmographyUseCase.execute(id = actorId)) {
            is OperationStatus.Success -> {
                Log.d("ActorBiographyViewModel", "Raw Filmography Data: ${status.value}")
                _actorFilmography.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit(status.exception.toString())
            }
        }
    }

}