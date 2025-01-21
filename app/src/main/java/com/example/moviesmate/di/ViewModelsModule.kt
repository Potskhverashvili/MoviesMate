package com.example.moviesmate.di

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
}