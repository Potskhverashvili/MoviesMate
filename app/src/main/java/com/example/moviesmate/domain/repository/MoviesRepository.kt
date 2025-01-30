package com.example.moviesmate.domain.repository

import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.domain.model.AboutActor
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.ActorFilmography
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput
import com.example.moviesmate.domain.model.HomePageMovies

interface MoviesRepository {
    suspend fun getGenresTypes(): OperationStatus<GenresType>
    suspend fun getCategoryMovies(page: Int): OperationStatus<CategoryMovies>
    suspend fun getMovieByGenre(genreId: Int, page: Int): OperationStatus<CategoryMovies>
    suspend fun getSearchedMovie(query: String): OperationStatus<SearchInput>
    suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetails>
    suspend fun getActorDetails(movieId: Int): OperationStatus<ActorDetails>
    suspend fun infoAboutActor(actorId: Int): OperationStatus<AboutActor>
    suspend fun getActorFilmography(actorId: Int): OperationStatus<ActorFilmography>

    suspend fun saveToFavorite(movie: Movie): OperationStatus<Unit>
    suspend fun deleteFromFavorite(movie: Movie): OperationStatus<Unit>
    suspend fun getAllSavedMovies(): OperationStatus<List<Movie>>


    suspend fun getUpcomingMovies(): OperationStatus<HomePageMovies>
    suspend fun getPopularMovies(): OperationStatus<HomePageMovies>

}