package com.example.moviesmate.domain.repository

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.model.SearchInput

interface MoviesRepository {
    suspend fun getGenresTypes(): OperationStatus<GenresType>
    suspend fun getCategoryMovies(page: Int): OperationStatus<CategoryMovies>
    suspend fun getMovieByGenre(genreId: Int, page:Int): OperationStatus<CategoryMovies>

    suspend fun getSearchedMovie(query: String): OperationStatus<SearchInput>

}