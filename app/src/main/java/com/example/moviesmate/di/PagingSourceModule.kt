package com.example.moviesmate.di

import com.example.moviesmate.data.pagingSourse.MoviesPagingSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val pagingSourceModule = module {
    singleOf(::MoviesPagingSource)
}