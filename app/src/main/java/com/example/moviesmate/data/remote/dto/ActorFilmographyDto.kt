package com.example.moviesmate.data.remote.dto

data class ActorFilmographyDto(
    val cast: List<CastDto>?,
    val id: Int?
){
    data class CastDto(
        val id: Int?,
        val poster_path: String?,
    )
}