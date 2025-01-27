package com.example.moviesmate.data

import com.example.moviesmate.data.remote.dto.ActorDetailsDto
import com.example.moviesmate.data.remote.dto.CategoryMoviesDto
import com.example.moviesmate.data.remote.dto.GenresTypeDto
import com.example.moviesmate.data.remote.dto.MovieDetailsDto
import com.example.moviesmate.data.remote.dto.SearchInputDto
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput

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

// dto to domain
fun CategoryMoviesDto.toCategoryMovies(): CategoryMovies {
    return CategoryMovies(
        pagingPage = this.pagingPageDto,
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

// dto to domain
fun SearchInputDto.toSearchInput(): SearchInput {
    return SearchInput(
        page = this.page,
        results = this.results.map { resultDto ->
            SearchInput.SearchedMovie(
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

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        backdrop_path = this.backdrop_path,
        genres = this.genres?.map { MovieDetails.Genre(it.id, it.name) },
        id = this.id,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        runtime = this.runtime,
        title = this.title,
        vote_average = this.vote_average
    )
}

fun ActorDetailsDto.toActorDetails(): ActorDetails {
    return ActorDetails(
        id = this.id,
        cast = this.cast?.map { castDto ->
            ActorDetails.Cast(
                cast_id = castDto.cast_id,
                credit_id = castDto.credit_id,
                id = castDto.id,
                original_name = castDto.original_name,
                profile_path = castDto.profile_path
            )
        }
    )
}