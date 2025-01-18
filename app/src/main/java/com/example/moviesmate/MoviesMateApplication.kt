package com.example.moviesmate

import android.app.Application
import com.example.moviesmate.di.firebaseModule
import com.example.moviesmate.di.firebaseRepositoryModule
import com.example.moviesmate.di.useCaseModule
import com.example.moviesmate.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesMateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesMateApplication)
            modules(firebaseModule, firebaseRepositoryModule, viewModelsModule, useCaseModule)
        }
    }

}