package com.example.moviesmate.data.repository

import com.example.moviesmate.core.ApiCallHelper
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.core.RoomCallHelper
import com.example.moviesmate.core.map
import com.example.moviesmate.data.local.entity.MovieDao
import com.example.moviesmate.data.local.entity.MovieDbo
import com.example.moviesmate.data.remote.service.MovieService
import com.example.moviesmate.data.toAboutActor
import com.example.moviesmate.data.toActorDetails
import com.example.moviesmate.data.toActorFilmography
import com.example.moviesmate.data.toCategoryMovies
import com.example.moviesmate.data.toGenresType
import com.example.moviesmate.data.toMovie
import com.example.moviesmate.data.toMovieDbo
import com.example.moviesmate.data.toMovieDetails
import com.example.moviesmate.data.toSearchInput
import com.example.moviesmate.domain.model.AboutActor
import com.example.moviesmate.domain.model.ActorDetails
import com.example.moviesmate.domain.model.ActorFilmography
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.GenresType
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput
import com.example.moviesmate.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val service: MovieService,
    private val movieDao: MovieDao

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

    override suspend fun infoAboutActor(actorId: Int): OperationStatus<AboutActor> {
        return ApiCallHelper.safeApiCall {
            service.infoAboutActor(actorId = actorId)
        }.map { aboutActorDto -> aboutActorDto.toAboutActor() }
    }

    override suspend fun getActorFilmography(actorId: Int): OperationStatus<ActorFilmography> {
        return ApiCallHelper.safeApiCall {
            service.getActorFilmography(actorId = actorId)
        }.map { actorFilmographyDto -> actorFilmographyDto.toActorFilmography() }
    }

    // ------------------------------------------------------------------------
    override suspend fun saveToFavorite(movie: Movie): OperationStatus<Unit> {
        return RoomCallHelper.safeRoomCall {
            movieDao.saveToFavorite(movie = movie.toMovieDbo())
        }
    }

    override suspend fun deleteFromFavorite(movie: Movie): OperationStatus<Unit> {
        return RoomCallHelper.safeRoomCall {
            movieDao.deleteFromFavorite(movie.toMovieDbo())
        }
    }

    override suspend fun getAllSavedMovies(): OperationStatus<List<Movie>> {
        return RoomCallHelper.safeRoomCall {
            movieDao.getAllFavorites().map { movieDbo -> movieDbo.toMovie() }
        }
    }
}