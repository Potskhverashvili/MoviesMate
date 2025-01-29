package com.example.moviesmate.data

import com.example.moviesmate.data.local.entity.MovieDbo
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.Movie
import com.example.moviesmate.domain.model.MovieDetails
import com.example.moviesmate.domain.model.SearchInput

// SearchAdapter to Dbo
fun CategoryMovies.Result.toMovieDbo(): MovieDbo {
    return MovieDbo(
        id = this.id,
        image = this.poster_path,
        title = this.title
    )
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

// MovieDbo to Movie
fun MovieDbo.toMovie() = Movie(
    id, image, title
)

// Movie to MovieDbo
fun Movie.toMovieDbo() = MovieDbo(
    id, image, title
)