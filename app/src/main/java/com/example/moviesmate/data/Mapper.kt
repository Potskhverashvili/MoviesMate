package com.example.moviesmate.data

import com.example.moviesmate.data.remote.dto.CategoryMoviesDto
import com.example.moviesmate.data.remote.dto.GenresTypeDto
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType

//Dto -> domain
fun GenresTypeDto.toGenresType(): GenresType {
    return GenresType(
        genres = this.genres.map { genreDto ->
            GenresType.Genre(
                id = genreDto.id,
                name = genreDto.name
            )
        }
    )
}

fun CategoryMoviesDto.toCategoryMovies(): CategoryMovies {
    return CategoryMovies(
        page = this.page,
        results = this.results.map { resultDto ->
            CategoryMovies.Result(
                adult = resultDto.adult,
                backdrop_path = resultDto.backdrop_path,
                genre_ids = resultDto.genre_ids,
                id = resultDto.id,
                original_language = resultDto.original_language,
                original_title = resultDto.original_title,
                overview = resultDto.overview,
                popularity = resultDto.popularity,
                poster_path = resultDto.poster_path,
                release_date = resultDto.release_date,
                title = resultDto.title,
                video = resultDto.video,
                vote_average = resultDto.vote_average,
                vote_count = resultDto.vote_count
            )
        },
        total_pages = this.total_pages,
        total_results = this.total_results
    )
}
