package com.example.moviesmate.data

import com.example.moviesmate.data.local.entity.MovieDbo
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput

// SearchAdapter to Dbo
fun CategoryMovies.Result.toMovieDbo(): MovieDbo? {
    return this.poster_path?.let {
        MovieDbo(
            id = this.id,
            image = it,
            title = this.title
        )
    }
}

fun CategoryMovies.Result.toMovie(): Movie {
    return Movie(
        id = this.id,
        image = this.poster_path,
        title = this.title
    )
}

fun SearchInput.SearchedMovie.toMovie(): Movie? {
    return this.poster_path?.let {
        Movie(
            id = this.id,
            image = it,
            title = this.title
        )
    }
}

fun MovieDetails.toMovie(): Movie? {
    return this.id?.let {
        this.poster_path?.let { it1 ->
            this.title?.let { it2 ->
                Movie(
                    id = it,
                    image = it1,
                    title = it2
                )
            }
        }
    }
}

fun MovieDetails.toMovieDbo(): MovieDbo? {
    val movieId = this.id ?: return null // If id is null, return null
    val movieTitle = this.title ?: this.original_title
    ?: "Unknown Title" // Use title, fallback to original_title
    val movieImage =
        this.poster_path ?: this.backdrop_path // Prefer poster_path, fallback to backdrop_path

    return movieImage?.let {
        MovieDbo(
            id = movieId, // Use the provided id
            image = it, // Use poster_path if available, else backdrop_path
            title = movieTitle // Ensure title is not null
        )
    }
}


// MovieDbo to Movie
fun MovieDbo.toMovie() = Movie(
    id, image, title
)

// Movie to MovieDbo
fun Movie.toMovieDbo(): MovieDbo {
    return MovieDbo(
        id = this.id ?: 0, // Default to 0 if id is null
        image = this.image ?: "", // Use poster_path first, fallback to backdrop_path
        title = this.title ?: "Unknown Title" // Default title if null
    )
}