package com.example.moviesmate.data.repository

import com.example.moviesmate.core.ApiCallHelper
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.core.map
import com.example.moviesmate.data.remote.dto.ActorDetailsDto
import com.example.moviesmate.data.remote.service.MovieService
import com.example.moviesmate.data.toActorDetails
import com.example.moviesmate.data.toCategoryMovies
import com.example.moviesmate.data.toGenresType
import com.example.moviesmate.data.toMovieDetails
import com.example.moviesmate.data.toSearchInput
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput
import com.example.moviesmate.domain.repository.MoviesRepository
import com.google.protobuf.Api

class MoviesRepositoryImpl(
    private val service: MovieService
) : MoviesRepository {

    override suspend fun getGenresTypes(): OperationStatus<GenresType> {
        return ApiCallHelper.safeApiCall {
            service.getGenresType()
        }.map { genresTypeDto -> genresTypeDto.toGenresType() }
    }

    override suspend fun getCategoryMovies(page: Int): OperationStatus<CategoryMovies> {
        return ApiCallHelper.safeApiCall {
            service.getCategoryMoves(page = page)
        }.map { categoryMoviesDto -> categoryMoviesDto.toCategoryMovies() }
    }

    override suspend fun getMovieByGenre(genreId: Int, page: Int): OperationStatus<CategoryMovies> {
        return ApiCallHelper.safeApiCall {
            service.getMoviesByGenre(genreId = genreId, page = page)
        }.map { categoryMoviesDto -> categoryMoviesDto.toCategoryMovies() }
    }


    override suspend fun getSearchedMovie(query: String): OperationStatus<SearchInput> {
        return ApiCallHelper.safeApiCall {
            service.searchMovies(query = query)
        }.map { searchInputDto -> searchInputDto.toSearchInput() }
    }

    override suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetails> {
        return ApiCallHelper.safeApiCall {
            service.getMovieDetails(movieId = movieId)
        }.map { movieDetailsDto -> movieDetailsDto.toMovieDetails() }
    }

    override suspend fun getActorDetails(movieId: Int): OperationStatus<ActorDetails> {
        return ApiCallHelper.safeApiCall {
            service.getActorDetails(movieId = movieId)
        }.map { actorDetailsDto -> actorDetailsDto.toActorDetails() }
    }


}