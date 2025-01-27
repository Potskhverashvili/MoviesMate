package com.example.moviesmate.di

import com.example.moviesmate.domain.usecases.AboutActorDetailsInfoUseCase
import com.example.moviesmate.domain.usecases.ActorDetailsUseCase
import com.example.moviesmate.domain.usecases.FetchGenresTypesUseCase
import com.example.moviesmate.domain.usecases.FetchMoviesByGenreUseCase
import com.example.moviesmate.domain.usecases.LoginUserUseCase
import com.example.moviesmate.domain.usecases.MovieDetailsUseCase
import com.example.moviesmate.domain.usecases.PasswordResetUseCase
import com.example.moviesmate.domain.usecases.RegisterNewUserUseCase
import com.example.moviesmate.domain.usecases.SearchMovieInputUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { RegisterNewUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
    factory { PasswordResetUseCase(get()) }
    factory { FetchGenresTypesUseCase(get()) }
    factory { FetchMoviesByGenreUseCase(get()) }
    factory { SearchMovieInputUseCase(get()) }
    factory { MovieDetailsUseCase(get()) }
    factory { ActorDetailsUseCase(get()) }
    factory { AboutActorDetailsInfoUseCase(get()) }
}