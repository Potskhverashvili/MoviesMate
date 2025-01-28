package com.example.moviesmate.core

object RoomCallHelper {
    suspend fun <SuccessType> safeRoomCall(
        dbCall: suspend () -> SuccessType
    ): OperationStatus<SuccessType> {
        return try {
            val result = dbCall.invoke()
            OperationStatus.Success(result)
        } catch (e: Exception) {
            OperationStatus.Failure(e)
        }
    }
}