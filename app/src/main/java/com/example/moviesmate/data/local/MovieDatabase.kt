package com.example.moviesmate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesmate.data.local.entity.MovieDao
import com.example.moviesmate.data.local.entity.MovieDbo

@Database(entities = [MovieDbo::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        fun create(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context = context,
                MovieDatabase::class.java,
                "MovieDatabase"
            ).build()
        }
    }
}