package com.example.moviesmate.data.remote.service

import com.example.moviesmate.core.Constants
import com.example.moviesmate.data.remote.dto.CategoryMoviesDto
import com.example.moviesmate.data.remote.dto.GenresTypeDto
import com.example.moviesmate.data.remote.dto.SearchInputDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("genre/movie/list?language=en")
    suspend fun getGenresType(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<GenresTypeDto>

    @GET("discover/movie")
    suspend fun getCategoryMoves(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): Response<CategoryMoviesDto>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ): Response<CategoryMoviesDto>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "ecf551cd79c0550487694d36dfc0514c"
    ): Response<SearchInputDto>

}