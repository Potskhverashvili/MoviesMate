package com.example.moviesmate.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.usecases.RegisterNewUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerNewUserUseCase: RegisterNewUserUseCase
) : ViewModel() {

    private val _registerFlow = MutableSharedFlow<String>()
    var registerFlow: SharedFlow<String> = _registerFlow

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private val _isLoadingState = MutableSharedFlow<Boolean>()
    val isLoadingState: SharedFlow<Boolean> = _isLoadingState

    fun registerNewUser(username: String, email: String, password: String) = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = registerNewUserUseCase.execute(username, email, password)) {
            is OperationStatus.Success -> {
                _registerFlow.emit(status.value.email.toString())
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.message)
            }
        }
        _isLoadingState.emit(false)
    }
}