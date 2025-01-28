package com.example.moviesmate.di

import androidx.room.Room
import com.example.moviesmate.data.local.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    // Provide the MovieDatabase instance
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java,
            "MovieDatabase"
        ).build()
    }

    // Provide the MovieDao instance
    single {
        get<MovieDatabase>().movieDao
    }
}
