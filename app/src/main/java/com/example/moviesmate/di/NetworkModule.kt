package com.example.moviesmate.di

import com.example.moviesmate.core.Constants.BASE_URL
import com.example.moviesmate.data.remote.service.MovieService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(MovieService::class.java)
    }

}