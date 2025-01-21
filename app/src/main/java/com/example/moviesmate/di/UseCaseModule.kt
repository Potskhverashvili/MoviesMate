package com.example.moviesmate.di

import com.example.moviesmate.domain.usecases.FetchGenresTypesUseCase
import com.example.moviesmate.domain.usecases.LoginUserUseCase
import com.example.moviesmate.domain.usecases.PasswordResetUseCase
import com.example.moviesmate.domain.usecases.RegisterNewUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { RegisterNewUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
    factory { PasswordResetUseCase(get()) }
    factory { FetchGenresTypesUseCase(get()) }
}