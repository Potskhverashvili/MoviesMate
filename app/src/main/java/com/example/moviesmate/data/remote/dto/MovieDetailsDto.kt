package com.example.moviesmate.data.remote.dto

data class MovieDetailsDto(
    val backdrop_path: String?,
    val genres: List<GenreDto>?,
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val runtime: Int?,
    val title: String?,
    val vote_average: Double?,
    val release_date: String?,
) {
    data class GenreDto(
        val id: Int?,
        val name: String?
    )

}