package com.example.moviesmate.presentation.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _registerFlow = MutableSharedFlow<String>()
    var registerFlow: SharedFlow<String> = _registerFlow

    private val _isLoadingState = MutableSharedFlow<Boolean>()
    val isLoadingState: SharedFlow<Boolean> = _isLoadingState

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    fun registerNewUser(email: String, username: String, password: String) = viewModelScope.launch {
        when (val status = firebaseRepository.registerNewUser(
            email = email,
            //username = username,
            password = password
        )) {
            is OperationStatus.Success -> {
                _registerFlow.emit(status.value.email.toString())
                Log.d("RegisterViewModel", "result = ${status.value}")
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.toString())
                Log.d("RegisterViewModel", "exception = ${status.exception}")
            }
        }
    }

}