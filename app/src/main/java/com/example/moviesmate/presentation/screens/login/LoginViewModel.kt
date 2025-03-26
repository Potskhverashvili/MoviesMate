package com.example.moviesmate.presentation.screens.login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.usecases.LoginUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _loginFlow = MutableSharedFlow<String>()
    var loginFlow: SharedFlow<String> = _loginFlow

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private val _isLoadingState = MutableSharedFlow<Boolean>()
    val isLoadingState: SharedFlow<Boolean> = _isLoadingState

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = loginUserUseCase.execute(email, password)) {
            is OperationStatus.Success -> {
                _loginFlow.emit(status.value.toString())
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception.message)

            }
        }
        _isLoadingState.emit(false)
    }

    fun isFormValid(email: String, password: String): Boolean {
        return when{
            email.isEmpty() || password.isEmpty() -> {
                viewModelScope.launch { _showError.emit("Please fill in all fields") }
                false
            }
            else -> true
        }
    }

}