package com.example.moviesmate.presentation.screens.containerFragment.actorBiography

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.AboutActor
import com.example.moviesmate.domain.usecases.AboutActorDetailsInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorBiographyViewModel(
    private val aboutActorDetailsInfoUseCase: AboutActorDetailsInfoUseCase
) : ViewModel() {

    private val _actorInfo = MutableStateFlow<AboutActor?>(null)
    val actorInfo = _actorInfo.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun getInfoAboutActor(actorId: Int) = viewModelScope.launch {
        when (val status = aboutActorDetailsInfoUseCase.execute(actorId = actorId)) {
            is OperationStatus.Success -> {
                _actorInfo.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit(status.exception.toString())
            }
        }
    }

}