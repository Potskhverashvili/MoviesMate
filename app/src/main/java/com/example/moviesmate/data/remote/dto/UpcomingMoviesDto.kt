package com.example.moviesmate.data.remote.dto

data class UpcomingMoviesDto(
    val page: Int?,
    val results: List<MovieDto>?,
) {
    data class MovieDto(
        val id: Int?,
        val backdrop_path: String?,
        val poster_path: String?,
        val title: String?,
    )
}