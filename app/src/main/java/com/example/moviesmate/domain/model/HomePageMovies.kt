package com.example.moviesmate.domain.model

data class HomePageMovies(
    val page: Int?,
    val results: List<Movie>?,
) {
    data class Movie(
        val id: Int?,
        val backdrop_path: String?,
        val poster_path: String?,
        val title: String?,
    )
}