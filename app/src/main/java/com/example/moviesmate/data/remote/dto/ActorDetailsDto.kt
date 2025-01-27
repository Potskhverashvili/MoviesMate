package com.example.moviesmate.data.remote.dto

data class ActorDetailsDto(
    val cast: List<CastDto>?,
    val id: Int?
){
    data class CastDto(
        val cast_id: Int?,
        val credit_id: String?,
        val id: Int?,
        val original_name: String?,
        val profile_path: String?
    )
}