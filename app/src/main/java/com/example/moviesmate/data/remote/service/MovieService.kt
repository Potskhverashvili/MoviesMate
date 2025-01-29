package com.example.moviesmate.data.remote.service

import com.example.moviesmate.core.Constants
import com.example.moviesmate.data.remote.dto.AboutActorDto
import com.example.moviesmate.data.remote.dto.ActorDetailsDto
import com.example.moviesmate.data.remote.dto.ActorFilmographyDto
import com.example.moviesmate.data.remote.dto.CategoryMoviesDto
import com.example.moviesmate.data.remote.dto.GenresTypeDto
import com.example.moviesmate.data.remote.dto.MovieDetailsDto
import com.example.moviesmate.data.remote.dto.SearchInputDto
import com.example.moviesmate.data.remote.dto.UpcomingMoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<SearchInputDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieDetailsDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getActorDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<ActorDetailsDto>

    @GET("person/{actorId}")
    suspend fun infoAboutActor(
        @Path("actorId") actorId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<AboutActorDto>

    @GET("person/{actor_id}/movie_credits")
    suspend fun getActorFilmography(
        @Path("actor_id") actorId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): Response<ActorFilmographyDto>

// Upcoming movies one page
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<UpcomingMoviesDto>
}
