package com.example.moviesmate.di

import com.example.moviesmate.presentation.screens.containerFragment.details.actorBiography.ActorBiographyViewModel
import com.example.moviesmate.presentation.screens.containerFragment.details.DetailsViewModel
import com.example.moviesmate.presentation.screens.containerFragment.favorites.FavoritesViewModel
import com.example.moviesmate.presentation.screens.containerFragment.home.HomeViewModel
import com.example.moviesmate.presentation.screens.containerFragment.profile.ProfileViewModel
import com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput.SearchInputViewModel
import com.example.moviesmate.presentation.screens.containerFragment.search.SearchViewModel
import com.example.moviesmate.presentation.screens.containerFragment.details.youtubeVideo.YoutubeVideoViewModel
import com.example.moviesmate.presentation.screens.login.LoginViewModel
import com.example.moviesmate.presentation.screens.passwordRecover.forgotPassword.ForgotPasswordViewModel
import com.example.moviesmate.presentation.screens.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SearchInputViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::ActorBiographyViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::YoutubeVideoViewModel)
}