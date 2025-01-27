package com.example.moviesmate.domain.repository

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput

interface MoviesRepository {
    suspend fun getGenresTypes(): OperationStatus<GenresType>
    suspend fun getCategoryMovies(page: Int): OperationStatus<CategoryMovies>
    suspend fun getMovieByGenre(genreId: Int, page:Int): OperationStatus<CategoryMovies>

    suspend fun getSearchedMovie(query: String): OperationStatus<SearchInput>

    suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetails>

    suspend fun getActorDetails(movieId: Int): OperationStatus<ActorDetails>
}