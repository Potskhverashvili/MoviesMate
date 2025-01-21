package com.example.moviesmate.domain.model

data class GenresType(
    val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}