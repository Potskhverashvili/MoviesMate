package com.example.moviesmate.di

import com.example.moviesmate.data.repository.FirebaseRepositoryImpl
import com.example.moviesmate.data.repository.MoviesRepositoryImpl
import com.example.moviesmate.domain.repository.FirebaseRepository
import com.example.moviesmate.domain.repository.MoviesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RepositoryModule = module {
    singleOf(::FirebaseRepositoryImpl) bind FirebaseRepository::class
    singleOf(::MoviesRepositoryImpl) bind MoviesRepository::class
}