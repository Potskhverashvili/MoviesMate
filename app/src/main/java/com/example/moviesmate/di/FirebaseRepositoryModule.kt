package com.example.moviesmate.di

import com.example.moviesmate.data.repository.FirebaseRepositoryImpl
import com.example.moviesmate.domain.repository.FirebaseRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val firebaseRepositoryModule = module {
    singleOf(::FirebaseRepositoryImpl) bind  FirebaseRepository::class
}