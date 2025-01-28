package com.example.moviesmate.domain.model

data class ActorFilmography(
    val cast: List<Cast>?,
    val id: Int?
){
    data class Cast(
        val id: Int?,
        val poster_path: String?,
    )
}
