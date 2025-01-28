package com.example.moviesmate.data

import com.example.moviesmate.data.local.entity.MovieDbo
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.model.Movie

// SearchAdapter to Dbo
fun CategoryMovies.Result.toMovieDbo(): MovieDbo {
    return MovieDbo(
        id = this.id,
        image = this.poster_path,
        title = this.title
    )
}

// MovieDbo to Movie
fun MovieDbo.toMovie() = Movie(
    id, image, title
)

// Movie to MovieDbo
fun Movie.toMovieDbo() = MovieDbo(
    id, image, title
)