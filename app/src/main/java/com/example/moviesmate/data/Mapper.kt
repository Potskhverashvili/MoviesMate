package com.example.moviesmate.data

import com.example.moviesmate.data.remote.dto.GenresTypeDto
import com.example.moviesmate.domain.model.GenresType

//Dto -> domain
fun GenresTypeDto.toGenresType(): GenresType {
    return GenresType(
        genres = this.genres.map { genreDto ->
            GenresType.Genre(
                id = genreDto.id,
                name = genreDto.name
            )
        }
    )
}