package com.example.moviesmate.data.remote.dto

data class GenresTypeDto(
    val genres: List<GenreDto>
) {
    data class GenreDto(
        val id: Int,
        val name: String
    )
}