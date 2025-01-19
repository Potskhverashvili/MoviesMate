package com.example.moviesmate.presentation.screens.passwordRecover.forgotPassword

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/*
class ForgotPasswordViewModel() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()




    private val _sendEmailState = MutableStateFlow<AuthState>(AuthState.Id)
    val sendEmailState: StateFlow<AuthState> = _sendEmailState

    // Trigger email with verification code
    fun sendVerificationCode(email: String) {
        _sendEmailState.update { AuthState.Loading }
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _sendEmailState.update { AuthState.Success("Verification email sent to $email") }
            } else {
                _sendEmailState.update {
                    AuthState.Error(
                        task.exception?.message ?: "Error sending email"
                    )
                }
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val error: String) : AuthState()
}*/
