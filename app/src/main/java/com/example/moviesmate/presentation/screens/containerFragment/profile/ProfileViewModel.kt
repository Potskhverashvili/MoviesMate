package com.example.moviesmate.presentation.screens.containerFragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.usecases.GetUserEmailUseCase
import com.example.moviesmate.domain.usecases.GetUserNameUseCase
import com.example.moviesmate.domain.usecases.LogOutUseCase
import com.example.moviesmate.domain.usecases.UpdateUserNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail = _userEmail.asStateFlow()

    fun getUsername() = viewModelScope.launch {
        when (val result = getUserNameUseCase.execute()) {
            is OperationStatus.Success -> {
                _username.emit(result.value.toString())
            }

            is OperationStatus.Failure -> {

            }
        }
    }

    fun updateUserName(updateName: String) = viewModelScope.launch {
        when (updateUserNameUseCase.execute(updateName)) {
            is OperationStatus.Success -> {
                getUsername()
            }

            is OperationStatus.Failure -> {

            }
        }
    }

    fun getUserEmail() = viewModelScope.launch {
        when (val status = getUserEmailUseCase.execute()) {
            is OperationStatus.Success -> {
                _userEmail.emit(status.value)
            }

            is OperationStatus.Failure -> {

            }
        }
    }


    fun logOut() = viewModelScope.launch {
        when (logOutUseCase.execute()) {
            is OperationStatus.Success -> {

            }

            is OperationStatus.Failure -> {

            }
        }
    }
}