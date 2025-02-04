package com.example.moviesmate.domain.usecases

import android.net.Uri
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.repository.FirebaseRepository

class UploadImageToFireStoreUseCase(
    private val firebaseRepository: FirebaseRepository
) {
    suspend fun execute(uri: Uri): OperationStatus<String> {
        return firebaseRepository.uploadImageToFireStore(uri)
    }
}