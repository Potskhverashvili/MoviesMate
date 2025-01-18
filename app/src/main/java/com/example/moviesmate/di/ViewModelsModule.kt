package com.example.moviesmate.di

import com.example.moviesmate.presentation.screens.register.RegisterViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val viewModelsModule = module {
    viewModelOf(::RegisterViewModel)
}