package com.example.moviesmate.domain.model

data class MovieDetails(
    val backdrop_path: String?,
    val genres: List<Genre>?,
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val runtime: Int?,
    val title: String?,
    val vote_average: Double?,
) {
    data class Genre(
        val id: Int?,
        val name: String?
    )
}