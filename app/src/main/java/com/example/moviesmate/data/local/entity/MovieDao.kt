package com.example.moviesmate.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToFavorite(movie: MovieDbo)

    @Delete
    suspend fun deleteFromFavorite(movie: MovieDbo)

    @Query("SELECT * FROM movie_table")
    suspend fun getAllFavorites(): List<MovieDbo>
}