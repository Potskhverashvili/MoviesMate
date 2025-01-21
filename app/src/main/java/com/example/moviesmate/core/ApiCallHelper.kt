package com.example.moviesmate.core

import retrofit2.Response

object ApiCallHelper {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): OperationStatus<T> {
        return try {
            val result = apiCall.invoke()
            OperationStatus.Success(result.body()!!)
        } catch (e: Exception) {
            OperationStatus.Failure(e)
        }
    }

}