package com.example.moviesmate

import android.app.Application
import com.example.moviesmate.di.RepositoryModule
import com.example.moviesmate.di.firebaseModule
import com.example.moviesmate.di.networkModule
import com.example.moviesmate.di.pagingSourceModule
import com.example.moviesmate.di.roomModule
import com.example.moviesmate.di.useCaseModule
import com.example.moviesmate.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesMateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesMateApplication)
            modules(
                firebaseModule,
                viewModelsModule,
                useCaseModule,
                networkModule,
                RepositoryModule,
                pagingSourceModule,
                roomModule
            )
        }
    }

}