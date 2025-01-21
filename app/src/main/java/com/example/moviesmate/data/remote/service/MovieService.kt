package com.example.moviesmate.data.remote.service

import com.example.moviesmate.core.Constants
import com.example.moviesmate.data.remote.dto.CategoryMoviesDto
import com.example.moviesmate.data.remote.dto.GenresTypeDto
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
}