package com.example.moviesmate.presentation.screens.containerFragment.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.usecases.GetUserEmailUseCase
import com.example.moviesmate.domain.usecases.GetUserNameUseCase
import com.example.moviesmate.domain.usecases.LogOutUseCase
import com.example.moviesmate.domain.usecases.UpdateUserNameUseCase
import com.google.firebase.auth.FirebaseAuth
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

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun getUsername() = viewModelScope.launch {
        _loading.emit(true)
        when (val result = getUserNameUseCase.execute()) {
            is OperationStatus.Success -> {
                _username.emit(result.value.toString())
                _error.emit(null)
            }

            is OperationStatus.Failure -> {
                _error.emit("Failed to fetch username")
            }
        }
        _loading.emit(false)
    }

    fun updateUserName(updateName: String) = viewModelScope.launch {
        _loading.emit(true)
        when (updateUserNameUseCase.execute(updateName)) {
            is OperationStatus.Success -> {
                getUsername()  //
                _error.emit(null)
            }

            is OperationStatus.Failure -> {
                _error.emit("Failed to update username")
            }
        }
        _loading.emit(false)
    }

    fun getUserEmail() = viewModelScope.launch {
        _loading.emit(true)
        when (val status = getUserEmailUseCase.execute()) {
            is OperationStatus.Success -> {
                _userEmail.emit(status.value)
                _error.emit(null)
            }

            is OperationStatus.Failure -> {
                _error.emit("Failed to fetch email")
            }
        }
        _loading.emit(false)
    }

    fun logOut() = viewModelScope.launch {
        _loading.emit(true)
        when (logOutUseCase.execute()) {
            is OperationStatus.Success -> {
                _error.emit(null)
            }

            is OperationStatus.Failure -> {
                _error.emit("Logout failed")
            }
        }
        _loading.emit(false)
    }

    fun onImageSelected(uri: Uri) {
        _selectedImageUri.value = uri
        // Optionally, you can upload the image to Firebase or perform other actions

    }

}